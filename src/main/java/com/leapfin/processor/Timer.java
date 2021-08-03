package com.leapfin.processor;

public class Timer {
    private final int timeoutLimitInMillis;
    private long startTime;
    private long endTime;

    public Timer(final int timeoutLimitInSeconds) {
        this.timeoutLimitInMillis = timeoutLimitInSeconds * 1000;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        endTime = startTime + timeoutLimitInMillis;
    }

    public boolean hasReachedTimeout() {
        return System.currentTimeMillis() >= endTime;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }
}
