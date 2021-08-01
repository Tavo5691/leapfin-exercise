import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Program {

    public static void main(String[] args) throws InterruptedException {
        final String target = "Lpfn";
        final long timeoutLimit = getTimeoutLimit(args);
        List<SearchResult> resultList = new ArrayList<>();

        System.out.printf("Searching for '%s'%n", target);
        System.out.printf("Timeout set to %s sec%n", timeoutLimit);

        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Callable<SearchResult>> tasks = new ArrayList<>();
        IntStream.rangeClosed(1, 10)
                .forEach(i -> tasks.add(new Processor(target, timeoutLimit)));

        List<Future<SearchResult>> futures = executor.invokeAll(tasks);

        for (Future<SearchResult> future : futures) {
            SearchResult searchResult;

            try {
                searchResult = future.get();

            } catch (InterruptedException | ExecutionException e) {
                System.err.println(e.getMessage());
                searchResult = new SearchResult(0L, 0L, "FAILURE");
            }

            resultList.add(searchResult);
        }

        executor.shutdown();
        printReport(resultList);
    }

    private static long getTimeoutLimit(String[] args) {
        try {
            return Long.parseLong(args[0]);

        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return 60L;
        }
    }

    private static void printReport(List<SearchResult> resultList) {
        System.out.println("******************** Report ********************");

        resultList.stream()
                .sorted()
                .forEach(searchResult -> System.out.println(searchResult.getReportLine()));

        OptionalDouble average = resultList.stream()
                .filter(SearchResult::isSuccessful)
                .flatMapToLong(searchResult -> LongStream.of(searchResult.getBytesPerMillis()))
                .average();

        System.out.printf("average=%s B/ms%n", average.orElse(0.0D));

        System.out.println("************************************************");
    }
}
