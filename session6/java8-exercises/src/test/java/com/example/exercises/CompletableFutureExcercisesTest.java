package com.example.exercises;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

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
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        cf = cf.thenApply(s -> s.toUpperCase());
        assertEquals("MESSAGE", cf.getNow(null));
    }

    @Test
    public void applyFunctionOnPreviousStageAsync() {
        // TODO: you need to make message to upper case with an async call. add Thread.sleep() in the function to make
        // the test pass
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        cf = cf.thenApplyAsync(s -> {
            try {
                Thread.sleep(1000);
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
        // TODO: you need to add the message to result
        StringBuilder result = new StringBuilder();
        CompletableFuture<String> cf =CompletableFuture.completedFuture("message");
        cf = cf.thenApply(s -> {
            result.append('c');
            return null;
        });
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
            .thenApplyAsync(CompletableFutureExcercisesTest::delay).thenApplyAsync(s -> s.toUpperCase());

        // TODO: Transform the string to lower case
        CompletableFuture<String> cf2 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay).thenApplyAsync(s -> s.toLowerCase());

        String toAppend = " from applyToEither";

        // TODO: append to whichever computation finishes first
        CompletableFuture<String> result = CompletableFuture.anyOf(cf1, cf2).thenApply(s -> {
            return s.toString() + toAppend;
        });
        assertTrue(result.join().endsWith(" from applyToEither"));
    }

    @Test
    public void consumeResultOfBoth() {
        String original = "Message";
        StringBuilder result = new StringBuilder();

        // TODO: Transform the string to upper case
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay).thenApplyAsync(String::toUpperCase);

        // TODO: Transform the string to lower case
        CompletableFuture<String> cf2 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay).thenApplyAsync(String::toLowerCase);


        // TODO: write into the StringBuilder cf1 result + cf2 result
        CompletableFuture<String> cf3 = CompletableFuture.allOf(cf1, cf2).thenApply(cfs -> {
            System.out.println(cfs);
            try {
                result.append(cf1.get()).append(cf2.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return result.toString();
        });

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
        CompletableFuture<String> cf2 = CompletableFuture.completedFuture(original)
            .thenApplyAsync(CompletableFutureExcercisesTest::delay);


        CompletableFuture cf = cf1.thenApply(String::toUpperCase).thenComposeAsync(
                upper -> cf2.thenApply(String::toLowerCase).thenApply(lower -> {
                    return upper + lower;
                })
        );
        assertEquals("MESSAGEmessage", cf.join());
    }

}
