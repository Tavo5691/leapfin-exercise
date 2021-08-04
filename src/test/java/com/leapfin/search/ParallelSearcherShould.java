package com.leapfin.search;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

public class ParallelSearcherShould {

    @Test
    public void returnAResultForEachWorkerUsedInTheSearch() throws InterruptedException {
        int timeoutInSeconds = 5;
        String target = "Lpfn";
        int numberOfWorkers = 10;

        ParallelSearcher searcher = new ParallelSearcher(timeoutInSeconds, target, numberOfWorkers);
        List<WorkerResult> resultList = searcher.search();

        assertEquals(numberOfWorkers, resultList.size());
    }
}