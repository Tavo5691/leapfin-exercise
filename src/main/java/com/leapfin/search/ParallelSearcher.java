package com.leapfin.search;

import com.leapfin.processor.InputProcessor;
import com.leapfin.processor.RandomDataStream;
import com.leapfin.processor.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelSearcher {

    private final int timeoutLimitInSeconds;
    private final String target;
    private final int numberOfWorkers;
    private final ExecutorService executorService;

    public ParallelSearcher(int timeoutInSeconds, String target, int numberOfWorkers) {
        this.timeoutLimitInSeconds = timeoutInSeconds;
        this.target = target;
        this.numberOfWorkers = numberOfWorkers;
        this.executorService = Executors.newFixedThreadPool(numberOfWorkers);
    }

    public List<WorkerResult> search() throws InterruptedException {
        System.out.printf("The Search parameters are: {timeout=%s sec, target=%s, numberOfWorkers=%s}\n",
            timeoutLimitInSeconds, target, numberOfWorkers);

        List<Future<WorkerResult>> futures = spawnWorkers();
        List<WorkerResult> workerResultList = collectWorkerResults(futures);
        shutdownWorkers();

        return workerResultList;
    }

    private List<Future<WorkerResult>> spawnWorkers() throws InterruptedException {
        List<Callable<WorkerResult>> workers = IntStream.rangeClosed(1, numberOfWorkers)
            .mapToObj(instantiateProcessorPerWorker())
            .collect(Collectors.toList());

        return executorService.invokeAll(workers);
    }

    private IntFunction<InputProcessor> instantiateProcessorPerWorker() {
        return i -> new InputProcessor(
            new RandomDataStream(),
            target,
            new Timer(timeoutLimitInSeconds)
        );
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
