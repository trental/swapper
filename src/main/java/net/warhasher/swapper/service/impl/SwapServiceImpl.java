package net.warhasher.swapper.service.impl;

import jakarta.transaction.Transactional;
import net.warhasher.swapper.data.Swap;
import net.warhasher.swapper.data.SwapCrawler;
import net.warhasher.swapper.entity.InventoryEntity;
import net.warhasher.swapper.event.SwapListExecuteEvent;
import net.warhasher.swapper.event.SwapCrawlEvent;
import net.warhasher.swapper.event.SwapCreatedEvent;
import net.warhasher.swapper.converter.SwapConverter;
import net.warhasher.swapper.data.SwapQueueKeeper;
import net.warhasher.swapper.dto.SwapDto;
import net.warhasher.swapper.entity.SwapEntity;
import net.warhasher.swapper.event.SwapDeletedEvent;
import net.warhasher.swapper.exception.ResourceNotFoundException;
import net.warhasher.swapper.repository.InventoryRepository;
import net.warhasher.swapper.repository.SwapRepository;
import net.warhasher.swapper.service.SwapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Service
public class SwapServiceImpl implements SwapService {

    private final SwapRepository swapRepository;
    private final InventoryRepository inventoryRepository;
    private final SwapConverter swapConverter;
    private final ApplicationEventPublisher eventPublisher;
    private final HashMap<UUID, SwapQueueKeeper> swapQueues;
    private static final Logger logger = LoggerFactory.getLogger(SwapServiceImpl.class);


    public SwapServiceImpl(
        SwapRepository swapRepository,
        InventoryRepository inventoryRepository,
        SwapConverter swapConverter,
        ApplicationEventPublisher eventPublisher) {
        this.swapRepository = swapRepository;
        this.inventoryRepository = inventoryRepository;
        this.swapConverter = swapConverter;
        this.eventPublisher = eventPublisher;
        this.swapQueues = new HashMap<>();
    }

    @Override
    public SwapDto createSwap(SwapDto swapDto) {
        SwapEntity swap = swapConverter.convertToEntity(swapDto);
        swap.setId(UUID.randomUUID()); // Generating UUID for the swap

        SwapEntity savedSwap = swapRepository.save(swap);

        logger.info("Publishing swap created event " + swap.toString());
        eventPublisher.publishEvent(new SwapCreatedEvent(this, savedSwap.getId()));

        return swapConverter.convertToDto(savedSwap);
    }

    @Override
    public void deleteSwap(UUID id) {
        SwapEntity existingSwap = swapRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Swap not found with ID: " + id));

        logger.info("Publishing swap deleted event " + id);
        eventPublisher.publishEvent(new SwapDeletedEvent(
                this,
                id,
                existingSwap.getInId(),
                existingSwap.getOutId()));

        swapRepository.delete(existingSwap);

    }

    @Async
    @EventListener
    public void handleSwapCreatedEvent(SwapCreatedEvent event) {
        UUID swapId = event.getSwapId();
        logger.info("Handling swap created event " + swapId);

        SwapEntity swapEntity = swapRepository.findById(swapId).get();
        Swap swap = new Swap(
                swapEntity.getId(),
                swapEntity.getInId(),
                swapEntity.getOutId(),
                swapEntity.getDeveloperId()
        );

        SwapQueueKeeper swapQueueKeeper;
        if (!swapQueues.containsKey(swap.getInId())) {
            swapQueueKeeper = new SwapQueueKeeper(swap.getInId());
            logger.info("Creating swapQueueKeeper inId " + swap.getInId());
            swapQueues.put(swap.getInId(), swapQueueKeeper);
        }
        swapQueueKeeper = swapQueues.get(swap.getInId());

        swapQueueKeeper.enqueue(swap);
        eventPublisher.publishEvent(new SwapCrawlEvent(this, swap));
    }

    @Async
    @EventListener
    public void handleSwapDeletedEvent(SwapDeletedEvent event) {
        UUID swapId = event.getSwapId();
        UUID inId = event.getInId();
        UUID outId = event.getOutId();
        logger.info("Handling swap deleted event " + swapId);

        if (!swapQueues.containsKey(inId)) {
            return;
        }

        swapQueues.get(inId).deleteSwapById(outId, swapId);

        if (swapQueues.get(inId).isEmpty()) {
            logger.info("Removing empty swapQueue inId " + inId);
            swapQueues.remove(inId);
        }
    }

    @Async
    @EventListener
    public void handleSwapCrawlEvent(SwapCrawlEvent event) {
        logger.info("Handling swap crawl event " + event.getSwapToCheck().getId());

        SwapCrawler swapCrawler = new SwapCrawler(swapQueues, event.getSwapToCheck());
        ArrayList<Swap> swapList = swapCrawler.startSwapCrawler();

        if (!swapList.isEmpty()) {
            eventPublisher.publishEvent(new SwapListExecuteEvent(this, swapList));
        }
    }

    @Async
    @EventListener
    @Transactional
    public void handleSwapListExecuteEvent(SwapListExecuteEvent event){
        ArrayList<Swap> swapList = event.getSwapList();
        logger.info("Handling execute swap list event for swap " + swapList.get(0).getId());

        for (Swap swap : swapList) {
            // get top swap from swap queue
            if (swap.getId() == null) {
                Swap candidateSwap = this.swapQueues.get(swap.getInId()).getSwapQueueMap().get(swap.getOutId()).peek();

                swap.setId(candidateSwap.getId());
                swap.setDeveloperId(candidateSwap.getDeveloperId());
            }
            // for developerId, increment equipment inId
            InventoryEntity inInventory = inventoryRepository.getInventory(swap.getDeveloperId(), swap.getInId());
            inventoryRepository.setInventory(
                    swap.getDeveloperId(),
                    swap.getInId(),
                    inInventory == null ? 1 : inInventory.getQuantity() + 1
            );

            // then for developerId, decrement equipment outId
            InventoryEntity outInventory = inventoryRepository.getInventory(swap.getDeveloperId(), swap.getOutId());
            inventoryRepository.setInventory(
                    swap.getDeveloperId(),
                    swap.getOutId(),
                    outInventory == null ? 0 : outInventory.getQuantity() - 1);

            // delete swap
            deleteSwap(swap.getId());
        }
    }
}
