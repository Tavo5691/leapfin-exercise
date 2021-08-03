package com.leapfin.search;

import com.leapfin.processor.InputProcessor;
import com.leapfin.processor.RandomDataStream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelSearcher {
    private final SearchParameters parameters;
    private final ExecutorService executorService;

    public ParallelSearcher(final SearchParameters parameters) {
        this.parameters = parameters;
        this.executorService = Executors.newFixedThreadPool(parameters.getNumberOfWorkers());
    }

    public List<WorkerResult> search() throws InterruptedException {
        List<Future<WorkerResult>> futures = spawnWorkers();
        List<WorkerResult> workerResultList = collectWorkerResults(futures);
        shutdownWorkers();

        return workerResultList;
    }

    private List<Future<WorkerResult>> spawnWorkers() throws InterruptedException {
        List<Callable<WorkerResult>> workers = IntStream.rangeClosed(1, parameters.getNumberOfWorkers())
                .mapToObj(i -> new InputProcessor(
                                new RandomDataStream(),
                                parameters.getTarget(),
                                parameters.extractTimeoutFrom()
                        ))
                .collect(Collectors.toList());

        return executorService.invokeAll(workers);
    }

    private List<WorkerResult> collectWorkerResults(final List<Future<WorkerResult>> futures) {
        List<WorkerResult> workerResultList = new ArrayList<>();

        for (Future<WorkerResult> future : futures) {
            WorkerResult workerResult;

            try {
                workerResult = future.get();

            } catch (InterruptedException | ExecutionException e) {
                System.err.println(e.getMessage());
                workerResult = new WorkerResult(0L, 0L, "FAILURE");
            }

            workerResultList.add(workerResult);
        }

        return workerResultList;
    }

    private void shutdownWorkers() {
        executorService.shutdown();
    }
}
