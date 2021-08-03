package com.leapfin.report;

import com.leapfin.search.WorkerResult;

import java.util.List;
import java.util.stream.LongStream;

public class ReportCreator {

    public static String createFrom(final List<WorkerResult> workerResultList) {
        StringBuilder sb = new StringBuilder();

        sb.append("******************** Report ********************\n");

        workerResultList.stream()
                .sorted()
                .forEach(result -> sb.append(writeReportLine(result)));

        sb.append(String.format("average=%s B/ms\n", calculateAverageSpeed(workerResultList)));

        sb.append("************************************************\n");

        return sb.toString();
    }

    private static String writeReportLine(final WorkerResult result) {
        if (!result.isSuccessful()) {
            return String.format("elapsed= byte_cnt= status=%s\n", result.getStatus());
        }

        return String.format("elapsed=%s ms byte_cnt=%s B status=%s\n",
                result.getElapsedTime(), result.getBytesRead(), result.getStatus()
        );
    }

    private static double calculateAverageSpeed(final List<WorkerResult> workerResultList) {
        return workerResultList.stream()
                .filter(WorkerResult::isSuccessful)
                .flatMapToLong(result -> LongStream.of(result.getProcessingSpeed()))
                .average()
                .orElse(0.0D);
    }
}
