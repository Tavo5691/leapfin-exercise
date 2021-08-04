package com.leapfin.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.leapfin.search.TestDataStream;
import com.leapfin.search.WorkerResult;
import org.junit.Test;

public class InputProcessorShould {

    public static final String TARGET = "Lpfn";
    public static final int TIMEOUT_IN_SECONDS = 60;

    @Test
    public void returnSuccessfulResultWhenProvidedWithInputThatContainsTheTargetString() {
        TestDataStream successfulDataStream = new TestDataStream(TARGET);
        Timer timer = new Timer(TIMEOUT_IN_SECONDS);

        InputProcessor processor = new InputProcessor(successfulDataStream, TARGET, timer);
        WorkerResult result = processor.call();

        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    public void answerBeforeTheTimeoutLimitWhenProvidedWithInputThatContainsTheTargetString() {
        TestDataStream successfulDataStream = new TestDataStream(TARGET);
        Timer timer = new Timer(TIMEOUT_IN_SECONDS);
        int timeoutLimitInMillis = TIMEOUT_IN_SECONDS * 1000;

        InputProcessor processor = new InputProcessor(successfulDataStream, TARGET, timer);
        WorkerResult result = processor.call();

        assertTrue(result.getElapsedTime() < timeoutLimitInMillis);
    }

    @Test
    public void timeoutWhenProvidedWithInputThatDoesNotContainTheTargetString() {
        TestDataStream timeoutDataStream = new TestDataStream("Not the target");
        int timeoutLimitInSeconds = 10;
        Timer shortTimer = new Timer(timeoutLimitInSeconds);
        int timeoutLimitInMillis = timeoutLimitInSeconds * 1000;

        InputProcessor processor = new InputProcessor(timeoutDataStream, TARGET, shortTimer);
        WorkerResult result = processor.call();

        assertEquals("TIMEOUT", result.getStatus());
        assertTrue(result.getElapsedTime() >= timeoutLimitInMillis);

    }

}