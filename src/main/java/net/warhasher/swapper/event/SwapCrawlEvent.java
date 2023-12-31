package net.warhasher.swapper.event;

import net.warhasher.swapper.data.Swap;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class SwapCrawlEvent extends ApplicationEvent {

    private final Swap swapToCheck;

    public SwapCrawlEvent(Object source, Swap swapToCheck) {
        super(source);
        this.swapToCheck = swapToCheck;
    }


    public Swap getSwapToCheck() {
        return swapToCheck;
    }
}
