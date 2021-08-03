package com.leapfin.search;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParallelSearcherShould {

    @Test
    public void returnAResultForEachWorkerUsedInTheSearch() throws InterruptedException {
        String timeoutInSeconds = "5";
        String[] args = { timeoutInSeconds };
        SearchParameters parameters = new SearchParameters(args);

        ParallelSearcher searcher = new ParallelSearcher(parameters);
        List<WorkerResult> resultList = searcher.search();

        assertEquals(10, resultList.size());
    }
}