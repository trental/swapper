package net.warhasher.swapper.event;

import lombok.Getter;
import net.warhasher.swapper.data.Swap;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;

@Getter
public class SwapListExecuteEvent extends ApplicationEvent {

    private final ArrayList<Swap> swapList;
    public SwapListExecuteEvent(Object source, ArrayList<Swap> swapList) {
        super(source);
        this.swapList = swapList;
    }
}
