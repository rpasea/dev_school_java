package com.example.exercises;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static junit.framework.TestCase.*;

public class CompletableFutureExcercisesTest {

    private static <T> T delay(T object) {
        int sleepDuration = new Random().nextInt(500) + 500;
        try {
            Thread.sleep(sleepDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Test
    public void applyFunctionOnPreviousStage() {
        // TODO: you need to make message to upper case
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(m -> m.toUpperCase());

        assertEquals("MESSAGE", cf.getNow(null));
    }

    @Test
    public void applyFunctionOnPreviousStageAsync() {
        // TODO: you need to make message to upper case with an async call. add Thread.sleep() in the function to make
        // the test pass
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(m -> m.toUpperCase());

        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }

    @Test
    public void consumeResultOfPreviousStage() {
        // TODO: you need to add the message to result
        StringBuilder result = new StringBuilder();
        CompletableFuture<String> cf =CompletableFuture.completedFuture("message");
        cf.thenAccept(m -> result.append(m));

        assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void exceptionHandler() {
        String errorMessage = "ERROR!";

        CompletableFuture<String> cf = CompletableFuture.completedFuture("message")
            .thenApplyAsync(CompletableFutureExcercisesTest::delay);

        // TODO: You need to attach an exception handler stage
        CompletableFuture<String> exceptionHandler = cf.exceptionally(ex -> "ERROR!");

        cf.completeExceptionally(new RuntimeException("completed exceptionally"));

        try {
            cf.join();
            fail("Should have thrown an exception");
        } catch(CompletionException ex) { // just for testing
            assertEquals("completed exceptionally", ex.getCause().getMessage());
        }


        assertEquals(errorMessage, exceptionHandler.join());
    }

    @Test
    public void applyFunctionToEither() {
        String original = "Message";

        // TODO: Transform the string to upper case
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay).thenApply(m -> m.toUpperCase());

        // TODO: Transform the string to lower case
        CompletableFuture<String> cf2 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay).thenApply(m -> m.toLowerCase());

        String toAppend = " from applyToEither";

        // TODO: append to whichever computation finishes first
        CompletableFuture<String> result = cf1.applyToEither(cf2, (m) -> m + toAppend);
        assertTrue(result.join().endsWith(" from applyToEither"));
    }

    @Test
    public void consumeResultOfBoth() {
        String original = "Message";
        StringBuilder result = new StringBuilder();

        // TODO: Transform the string to upper case
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay).thenApply(m -> m.toUpperCase());

        // TODO: Transform the string to lower case
        CompletableFuture<String> cf2 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay).thenApply(m -> m.toLowerCase());


        // TODO: write into the StringBuilder cf1 result + cf2 result
        CompletableFuture<Void> cf3 = cf1.thenAcceptBoth(cf2, (m1, m2) -> {
                                                                                result.append(m1);
                                                                                result.append(m2);
                                                                            });

        cf3.join();
        assertEquals("MESSAGEmessage", result.toString());
    }

    @Test
    @Ignore
    public void composeTwoFutures() {
        String original = "Message";

        // TODO: Transform the string to upper case
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay).thenApply(m -> m.toUpperCase());

        // TODO: Run the completable future below once the first future finishes and concatenate the results of the 2
        // futures. You need to get something like "MESSAGEmessage.
        //cf1.thenCompose(s -> original.toLowerCase() + s);


        // TODO: Transform the string to lower case
        //CompletableFuture<String> cf2 = CompletableFuture.completedFuture(original)
        //    .thenApplyAsync(CompletableFutureExcercisesTest::delay);

        CompletableFuture cf = null;
        assertEquals("MESSAGEmessage", cf.join());
    }

}
