import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class Processor implements Callable<SearchResult> {
    private final String target;
    private final long timeoutLimitInMillis;

    public Processor(String target, long timeoutLimitInSeconds) {
        this.target = target;
        this.timeoutLimitInMillis = timeoutLimitInSeconds * 1000;
    }

    @Override
    public SearchResult call() {
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + timeoutLimitInMillis;

        Random random = ThreadLocalRandom.current();
        long bytesRead = 0L;

        while (true) {
            if (hasReached(endTime)) return new SearchResult(getElapsedTime(startTime), bytesRead, "TIMEOUT");

            final byte[] data = getNextChunkOfData(random);
            bytesRead += data.length;

            final String parsedData = new String(data, StandardCharsets.UTF_8);

            if (parsedData.equals(target)) break;
        }

        return new SearchResult(getElapsedTime(startTime), bytesRead, "SUCCESS");
    }

    private boolean hasReached(long endTime) {
        return System.currentTimeMillis() >= endTime;
    }

    private long getElapsedTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    public byte[] getNextChunkOfData(Random random) {
        byte[] data = new byte[target.length()];
        random.nextBytes(data);

        return data;
    }
}
