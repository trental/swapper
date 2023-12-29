package net.warhasher.swapper.event;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class SwapDeletedEvent extends ApplicationEvent {

    private final UUID swapId;
    private final UUID inId;
    private final UUID outId;

    public SwapDeletedEvent(Object source, UUID swapId, UUID inId, UUID outId) {
        super(source);
        this.swapId = swapId;
        this.inId = inId;
        this.outId = outId;
    }

    public UUID getSwapId() {
        return swapId;
    }

    public UUID getInId() {
        return inId;
    }

    public UUID getOutId() {
        return outId;
    }
}
