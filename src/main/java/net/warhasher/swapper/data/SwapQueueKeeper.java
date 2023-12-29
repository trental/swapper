package net.warhasher.swapper.data;

import net.warhasher.swapper.service.impl.SwapServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.UUID;

public class SwapQueueKeeper {

    private static final Logger logger = LoggerFactory.getLogger(SwapServiceImpl.class);
    private final UUID inId;
    private final UUID outId;
    private final HashMap<UUID, SwapQueue> swapQueueMap;

    // add red-black tree for sorted retrieval
    public SwapQueueKeeper(UUID inId, UUID outId) {
        this.inId = inId;
        this.outId = outId;
        swapQueueMap = new HashMap<>();
    }

    public void enqueue(Swap swap) {
        UUID outId = swap.getOutId();
        SwapQueue swapQueue;

        if (!swapQueueMap.containsKey(outId)) {
            swapQueue = new SwapQueue(this.inId, this.outId);
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

    }
}
