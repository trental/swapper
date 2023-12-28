package net.warhasher.swapper.data;

import java.util.HashMap;
import java.util.UUID;

public class SwapQueueKeeper {

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
        System.out.println("Adding swap to keeper " + swap.toString());
        swapQueue.enqueue(swap);
    }
}
