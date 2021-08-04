package com.leapfin.search;

public class WorkerResult implements Comparable<WorkerResult> {

    private final Long elapsedTime;
    private final Long bytesRead;
    private final String status;

    public WorkerResult(final Long elapsedTime, final Long bytesRead, final String status) {
        this.elapsedTime = elapsedTime;
        this.bytesRead = bytesRead;
        this.status = status;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public Long getBytesRead() {
        return bytesRead;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSuccessful() {
        return status.equals("SUCCESS");
    }

    public long getProcessingSpeed() {
        return bytesRead / elapsedTime;
    }

    @Override
    public int compareTo(final WorkerResult other) {
        return other.getElapsedTime().compareTo(elapsedTime);
    }

    @Override
    public String toString() {
        return "WorkerResult{" +
            "elapsedTime=" + elapsedTime +
            ", bytesRead=" + bytesRead +
            ", status='" + status + '\'' +
            '}';
    }
}
