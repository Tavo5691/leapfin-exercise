public class SearchResult implements Comparable<SearchResult> {
    private final Long elapsedTime;
    private final Long bytesRead;
    private final String status;

    public SearchResult(Long elapsedTime, Long bytesRead, String status) {
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

    public String getReportLine() {
        if (!status.equals("SUCCESS")) return String.format("elapsed= byte_cnt= status=%s", status);

        return String.format("elapsed=%s ms byte_cnt=%s B status=%s", elapsedTime, bytesRead, status);
    }

    public boolean isSuccessful() {
        return status.equals("SUCCESS");
    }

    public long getBytesPerMillis() {
        return bytesRead / elapsedTime;
    }

    @Override
    public int compareTo(SearchResult other) {
        return other.getElapsedTime().compareTo(elapsedTime);
    }

    @Override
    public String toString() {
        return "Result{" +
                "elapsedTime=" + elapsedTime +
                ", bytesRead=" + bytesRead +
                ", status='" + status + '\'' +
                '}';
    }
}
