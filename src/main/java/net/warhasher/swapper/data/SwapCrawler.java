package net.warhasher.swapper.data;

import net.warhasher.swapper.service.impl.SwapServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.util.*;

public class SwapCrawler {

    private final HashMap<UUID, SwapQueueKeeper> swapQueues;
    private final Swap swapToCheck;
    private static final Logger logger = LoggerFactory.getLogger(SwapServiceImpl.class);

    public SwapCrawler(HashMap<UUID, SwapQueueKeeper> swapQueues, Swap swapToCheck) {
        this.swapQueues = swapQueues;
        this.swapToCheck = swapToCheck;
    }

    public ArrayList<Swap> startSwapCrawler(){
        UUID startingId = swapToCheck.getOutId();
        UUID endingId = swapToCheck.getInId();
        HashSet<UUID> visitedQueueKeepers = new HashSet<>();
        LinkedList<UUID> swapQueuesToVisit = new LinkedList<>();

        ArrayList<Swap> swapList = new ArrayList<>();
        HashMap<UUID, UUID> swapTrails = new HashMap<>();
        boolean cycleFound = false;

        if (!swapQueues.containsKey(startingId)) {
            return swapList;
        }

        UUID currentId;

        logger.info("Visiting " + startingId + " <- " + endingId);
        swapTrails.put(startingId, endingId);

        visitedQueueKeepers.add(startingId);
        swapQueuesToVisit.addLast(startingId);

        SwapQueueKeeper currentSwapQueueKeeper;
        HashMap<UUID, SwapQueue> currentSwapQueueMap;
        while(!swapQueuesToVisit.isEmpty() && !cycleFound) {
            currentId = swapQueuesToVisit.removeFirst();
            if (swapQueues.containsKey(currentId)) {
                currentSwapQueueKeeper = swapQueues.get(currentId);
                currentSwapQueueMap = currentSwapQueueKeeper.getSwapQueueMap();
                for (Map.Entry<UUID, SwapQueue> entry : currentSwapQueueMap.entrySet()) {
                    if (!visitedQueueKeepers.contains(entry.getKey())) {
                        logger.info("Visiting " + entry.getKey() + " <- " + currentId);
                        swapTrails.put(entry.getKey(), currentId);

                        visitedQueueKeepers.add(entry.getKey());
                        swapQueuesToVisit.addLast(entry.getKey());

                        if (entry.getKey().equals(endingId)) {
                            cycleFound = true;
                            logger.info("Solution found");
                        }
                    }
                    if (cycleFound) {
                        break;
                    }
                }
            }

            if (cycleFound) {
                UUID stepStart = null;
                UUID stepEnd = endingId;

                swapList.add(swapToCheck);
                while (stepStart != startingId) {
                    stepStart = swapTrails.get(stepEnd);
                    swapList.add(new Swap(null, stepStart, stepEnd, null));
                    stepEnd = stepStart;
                }

                logger.info(swapList.toString());
            }
        }
        return swapList;
    }
}
