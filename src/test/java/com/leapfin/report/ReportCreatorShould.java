package com.leapfin.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.leapfin.search.WorkerResult;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReportCreatorShould {

    @Test
    public void orderTheResultsBasedOnStatusAndReturnTheCompleteReport() {
        List<WorkerResult> workerResultList = new ArrayList<>();
        workerResultList.add(new WorkerResult(0L, 0L, "FAILURE"));
        workerResultList.add(new WorkerResult(60000L, 123456789L, "TIMEOUT"));
        workerResultList.add(new WorkerResult(45000L, 123456789L, "SUCCESS"));

        String report = ReportCreator.createFrom(workerResultList);

        assertEquals(expectedReport(), report);
    }

    private String expectedReport() {
        return "******************** Report ********************\n" +
            "elapsed= byte_cnt= status=TIMEOUT\n" +
            "elapsed=45000 ms byte_cnt=123456789 B status=SUCCESS\n" +
            "elapsed= byte_cnt= status=FAILURE\n" +
            "average=2743.0 B/ms\n" +
            "************************************************\n";
    }

    @Test
    public void calculateTheAverageProcessingSpeedOfAllTheWorkers() {
        List<WorkerResult> workerResultList = new ArrayList<>();
        workerResultList.add(new WorkerResult(10000L, 100000L, "SUCCESS"));
        workerResultList.add(new WorkerResult(10000L, 100000L, "SUCCESS"));
        workerResultList.add(new WorkerResult(10000L, 100000L, "SUCCESS"));
        workerResultList.add(new WorkerResult(60000L, 123456789L, "TIMEOUT"));

        String report = ReportCreator.createFrom(workerResultList);

        assertTrue(report.contains("average=10.0 B/ms"));
    }


}