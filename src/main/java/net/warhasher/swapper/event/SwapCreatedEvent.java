package net.warhasher.swapper.event;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class SwapCreatedEvent extends ApplicationEvent {

    private final UUID swapId;

    public SwapCreatedEvent(Object source, UUID swapId) {
        super(source);
        this.swapId = swapId;
    }

    public UUID getSwapId() {
        return swapId;
    }
}
