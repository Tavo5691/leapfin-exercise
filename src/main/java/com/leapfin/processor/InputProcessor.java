package com.leapfin.processor;

import com.leapfin.search.WorkerResult;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

public class InputProcessor implements Callable<WorkerResult> {

    private final DataStream dataStream;
    private final String target;
    private final Timer timer;

    public InputProcessor(final DataStream dataStream, final String target, final Timer timer) {
        this.dataStream = dataStream;
        this.target = target;
        this.timer = timer;
    }

    @Override
    public WorkerResult call() {
        return findMatchOrTimeout();
    }

    private WorkerResult findMatchOrTimeout() {
        timer.start();

        while (!timer.hasReachedTimeout()) {
            final String parsedData = getNextChunkOfData();

            if (parsedData.contains(target)) {
                return new WorkerResult(timer.getElapsedTime(), dataStream.getBytesRead(), "SUCCESS");
            }
        }

        return new WorkerResult(timer.getElapsedTime(), dataStream.getBytesRead(), "TIMEOUT");
    }

    private String getNextChunkOfData() {
        byte[] data = dataStream.read(target.length());

        return new String(data, StandardCharsets.UTF_8);
    }
}
