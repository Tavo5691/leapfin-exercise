package com.leapfin.processor;

import com.leapfin.processor.InputProcessor;
import com.leapfin.search.TestDataStream;
import com.leapfin.search.WorkerResult;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InputProcessorShould {

    @Test
    public void returnSuccessfulResultWhenProvidedWithInputThatContainsTheTargetString() throws Exception {
        TestDataStream successfulDataStream = new TestDataStream("Lpfn");

        InputProcessor processor = new InputProcessor(successfulDataStream, "Lpfn", 60);
        WorkerResult result = processor.call();

        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    public void answerBeforeTheTimeoutLimitWhenProvidedWithInputThatContainsTheTargetString() throws Exception {
        TestDataStream successfulDataStream = new TestDataStream("Lpfn");
        int timeoutLimitInSeconds = 60;
        int timeoutLimitInMillis = timeoutLimitInSeconds * 1000;

        InputProcessor processor = new InputProcessor(successfulDataStream, "Lpfn", timeoutLimitInSeconds);
        WorkerResult result = processor.call();

        assertTrue(result.getElapsedTime() < timeoutLimitInMillis);
    }

    @Test
    public void timeoutWhenProvidedWithInputThatDoesNotContainTheTargetString() throws Exception {
        TestDataStream timeoutDataStream = new TestDataStream("Not the target");
        int timeoutLimitInSeconds = 10;
        int timeoutLimitInMillis = timeoutLimitInSeconds * 1000;

        InputProcessor processor = new InputProcessor(timeoutDataStream, "Lpfn", timeoutLimitInSeconds);
        WorkerResult result = processor.call();

        assertEquals("TIMEOUT", result.getStatus());
        assertTrue(result.getElapsedTime() >= timeoutLimitInMillis);

    }

}