package com.example.exercises;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static java.lang.Thread.*;
import static junit.framework.TestCase.*;

public class CompletableFutureExcercisesTest {

    private static <T> T delay(T object) {
        int sleepDuration = new Random().nextInt(500) + 500;
        try {
            sleep(sleepDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Test
    public void applyFunctionOnPreviousStage() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        cf = cf.thenApply(s -> s = s.toUpperCase());

        assertEquals("MESSAGE", cf.getNow(null));
    }

    @Test
    public void applyFunctionOnPreviousStageAsync() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        cf = cf.thenApplyAsync(s -> {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return s.toUpperCase();

        });

        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }

    @Test
    public void consumeResultOfPreviousStage() {
        final StringBuilder result = new StringBuilder();
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        cf.thenAccept(s -> result.append(s));

        assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void exceptionHandler() {
        String errorMessage = "ERROR!";

        CompletableFuture<String> cf = CompletableFuture.completedFuture("message")
            .thenApplyAsync(CompletableFutureExcercisesTest::delay);

        // TODO: You need to attach an exception handler stage
        CompletableFuture<String> exceptionHandler = cf.exceptionally(ex -> errorMessage);

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
        CompletableFuture<Void> cf1 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay)
                .thenAccept(s -> s = s.toUpperCase());


        // TODO: Transform the string to lower case
        CompletableFuture<Void> cf2 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay)
                .thenAccept(s -> s = s.toLowerCase());

        String toAppend = " from applyToEither";

        // TODO: append to whichever computation finishes first
        CompletableFuture<String> result = null;
        assertTrue(result.join().endsWith(" from applyToEither"));
    }

    @Test
    public void consumeResultOfBoth() {
        String original = "Message";
        StringBuilder result = new StringBuilder();

        // TODO: Transform the string to upper case
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay);

        // TODO: Transform the string to lower case
        CompletableFuture<String> cf2 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay);


        // TODO: write into the StringBuilder cf1 result + cf2 result
        CompletableFuture<String> cf3 = null;

        cf3.join();
        assertEquals("MESSAGEmessage", result.toString());
    }

    @Test
    public void composeTwoFutures() {
        String original = "Message";

        // TODO: Transform the string to upper case
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay);

        // TODO: Run the completable future below once the first future finishes and concatenate the results of the 2
        // futures. You need to get something like "MESSAGEmessage.

        // TODO: Transform the string to lower case
        //CompletableFuture<String> cf2 = CompletableFuture.completedFuture(original)
        //    .thenApplyAsync(CompletableFutureExcercisesTest::delay);

        CompletableFuture cf = null;
        assertEquals("MESSAGEmessage", cf.join());
    }

}
