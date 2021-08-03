package com.leapfin;

import com.leapfin.report.ReportCreator;
import com.leapfin.search.ParallelSearcher;
import com.leapfin.search.SearchParameters;
import com.leapfin.search.WorkerResult;

import java.util.List;

public class Program {

    public static void main(String[] args) throws InterruptedException {
        SearchParameters parameters = new SearchParameters(args);
        System.out.println("The Search parameters are: " + parameters);

        List<WorkerResult> workerResultList = executeParallelSearch(parameters);
        createAndShowReport(workerResultList);
    }

    private static List<WorkerResult> executeParallelSearch(final SearchParameters parameters) throws InterruptedException {
        ParallelSearcher searcher = new ParallelSearcher(parameters);

        return searcher.search();
    }

    private static void createAndShowReport(final List<WorkerResult> workerResultList) {
        String report = ReportCreator.createFrom(workerResultList);

        System.out.println(report);
    }
}
