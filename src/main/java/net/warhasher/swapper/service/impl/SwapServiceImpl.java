package net.warhasher.swapper.service.impl;

import net.warhasher.swapper.data.Swap;
import net.warhasher.swapper.event.SwapCreatedEvent;
import net.warhasher.swapper.converter.SwapConverter;
import net.warhasher.swapper.data.SwapQueueKeeper;
import net.warhasher.swapper.dto.SwapDto;
import net.warhasher.swapper.entity.SwapEntity;
import net.warhasher.swapper.exception.ResourceNotFoundException;
import net.warhasher.swapper.repository.SwapRepository;
import net.warhasher.swapper.service.SwapService;
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

        System.out.println("Publishing event for swap " + swap.toString());
        eventPublisher.publishEvent(new SwapCreatedEvent(this, savedSwap.getId()));

        return swapConverter.convertToDto(savedSwap);
    }

    @Override
    public void deleteSwap(UUID id) {
        SwapEntity existingSwap = swapRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Swap not found with ID: " + id));
        swapRepository.delete(existingSwap);
    }

    @Async
    @EventListener
    public void handleSwapCreatedEvent(SwapCreatedEvent event) {
        System.out.println("Handling event for swap " + event.toString());

        UUID swapId = event.getSwapId();

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
}
