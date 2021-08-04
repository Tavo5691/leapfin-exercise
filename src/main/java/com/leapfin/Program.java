package com.leapfin;

import com.leapfin.report.ReportCreator;
import com.leapfin.search.ParallelSearcher;
import com.leapfin.search.WorkerResult;
import java.util.List;

public class Program {

    public static final int DEFAULT_TIMEOUT = 60;
    public static final int DEFAULT_NUMBER_OF_WORKERS = 10;
    public static final String DEFAULT_TARGET = "Lpfn";

    public static void main(String[] args) throws InterruptedException {
        int timeoutInSeconds = extractTimeoutFrom(args);
        List<WorkerResult> workerResultList = executeParallelSearch(timeoutInSeconds);
        createAndShowReport(workerResultList);
    }

    private static int extractTimeoutFrom(final String[] args) {
        try {
            return Integer.parseInt(args[0]);

        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return DEFAULT_TIMEOUT;
        }
    }

    private static List<WorkerResult> executeParallelSearch(final int timeoutInSeconds) throws InterruptedException {
        ParallelSearcher searcher = new ParallelSearcher(timeoutInSeconds, DEFAULT_TARGET, DEFAULT_NUMBER_OF_WORKERS);

        return searcher.search();
    }

    private static void createAndShowReport(final List<WorkerResult> workerResultList) {
        String report = ReportCreator.createFrom(workerResultList);

        System.out.println(report);
    }
}
