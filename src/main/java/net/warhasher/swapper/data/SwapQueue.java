package net.warhasher.swapper.data;

import lombok.Getter;

import java.util.LinkedList;
import java.util.UUID;

@Getter
public class SwapQueue {

    private final UUID inId;
    private final UUID outId;
    private final LinkedList<Swap> queue;

    public SwapQueue(UUID inId, UUID outId) {
        this.inId = inId;
        this.outId = outId;
        this.queue = new LinkedList<>();
    }

    public void enqueue(Swap swap){
        System.out.println("Adding swap to queue " + swap.toString());
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
}
