package net.warhasher.swapper.data;

import net.warhasher.swapper.service.impl.SwapServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.UUID;

public class SwapQueueKeeper {

    private static final Logger logger = LoggerFactory.getLogger(SwapServiceImpl.class);
    private final UUID inId;
    private final HashMap<UUID, SwapQueue> swapQueueMap;

    // add red-black tree for sorted retrieval
    public SwapQueueKeeper(UUID inId) {
        this.inId = inId;
        swapQueueMap = new HashMap<>();
    }

    public void enqueue(Swap swap) {
        UUID outId = swap.getOutId();
        SwapQueue swapQueue;

        if (!swapQueueMap.containsKey(outId)) {
            swapQueue = new SwapQueue(this.inId, outId);
            logger.info("Creating swapQueue outId " + outId);
            swapQueueMap.put(outId, swapQueue);
        } else {
            swapQueue = swapQueueMap.get(outId);
        }

        // add to red black tree too
        logger.info("Adding swap to keeper " + swap.toString());
        swapQueue.enqueue(swap);
    }

    public void deleteSwapById(UUID outId, UUID swapId) {
        if (!swapQueueMap.containsKey(outId)) {
            return;
        }

        swapQueueMap.get(outId).deleteFromQueue(swapId);

        if (swapQueueMap.get(outId).isEmpty()) {
            logger.info("Removing empty swapQueue outId " + outId);
            swapQueueMap.remove(outId);
        }

    }

    public boolean isEmpty() {
        return swapQueueMap.isEmpty();
    }

    public boolean hasSwapQueue(UUID outId){
        return swapQueueMap.containsKey(outId);
    }

    public HashMap<UUID, SwapQueue> getSwapQueueMap() {
        return swapQueueMap;
    }
}
