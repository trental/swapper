package net.warhasher.swapper.service.impl;

import net.warhasher.swapper.data.Swap;
import net.warhasher.swapper.event.SwapCreatedEvent;
import net.warhasher.swapper.converter.SwapConverter;
import net.warhasher.swapper.data.SwapQueueKeeper;
import net.warhasher.swapper.dto.SwapDto;
import net.warhasher.swapper.entity.SwapEntity;
import net.warhasher.swapper.event.SwapDeletedEvent;
import net.warhasher.swapper.exception.ResourceNotFoundException;
import net.warhasher.swapper.repository.SwapRepository;
import net.warhasher.swapper.service.SwapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class SwapServiceImpl implements SwapService {

    private final SwapRepository swapRepository;
    private final SwapConverter swapConverter;
    private final ApplicationEventPublisher eventPublisher;
    private final HashMap<UUID, SwapQueueKeeper> swapQueues;
    private static final Logger logger = LoggerFactory.getLogger(SwapServiceImpl.class);


    public SwapServiceImpl(
        SwapRepository swapRepository,
        SwapConverter swapConverter,
        ApplicationEventPublisher eventPublisher) {
        this.swapRepository = swapRepository;
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
            swapQueueKeeper = new SwapQueueKeeper(swap.getInId(), swap.getOutId());
            swapQueues.put(swap.getInId(), swapQueueKeeper);
        }
        swapQueueKeeper = swapQueues.get(swap.getInId());

        swapQueueKeeper.enqueue(swap);
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
    }
}
