package net.warhasher.swapper.data;

import lombok.Getter;
import net.warhasher.swapper.service.impl.SwapServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.UUID;

@Getter
public class SwapQueue {

    private final UUID inId;
    private final UUID outId;
    private final LinkedList<Swap> queue;
    private static final Logger logger = LoggerFactory.getLogger(SwapServiceImpl.class);

    public SwapQueue(UUID inId, UUID outId) {
        this.inId = inId;
        this.outId = outId;
        this.queue = new LinkedList<>();
    }

    public void enqueue(Swap swap){
        logger.info("Adding swap to queue " + swap.toString());
        queue.addLast(swap);
    }

    public Swap dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return queue.removeFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public Swap deleteFromQueue(UUID swapId) {
        for (Swap swap : queue) {
            if (swap.getId().equals(swapId)) {
                queue.remove(swap);
                logger.info("Deleted swap " + swapId + " from queue");
                return swap;
            }
        }

        return null;
    }
}
