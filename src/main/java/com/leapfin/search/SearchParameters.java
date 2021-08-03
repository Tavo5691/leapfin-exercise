package com.leapfin.search;

public class SearchParameters {
    public static final int DEFAULT_TIMEOUT = 60;
    public static final int DEFAULT_NUMBER_OF_WORKERS = 10;
    public static final String DEFAULT_TARGET = "Lpfn";

    private final int timeoutLimitInSeconds;
    private final int numberOfWorkers;
    private final String target;

    public SearchParameters(final String[] args) {
        this.timeoutLimitInSeconds = extractTimeoutFrom(args);

        this.numberOfWorkers = DEFAULT_NUMBER_OF_WORKERS;
        this.target = DEFAULT_TARGET;
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public String getTarget() {
        return target;
    }

    public int extractTimeoutFrom() {
        return timeoutLimitInSeconds;
    }

    private int extractTimeoutFrom(final String[] args) {
        try {
            return Integer.parseInt(args[0]);

        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return DEFAULT_TIMEOUT;
        }
    }

    @Override
    public String toString() {
        return "SearchParameters{" +
                "numberOfWorkers=" + numberOfWorkers +
                ", target='" + target + '\'' +
                ", timeoutLimitInSeconds=" + timeoutLimitInSeconds +
                '}';
    }
}
