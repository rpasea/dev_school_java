package com.example.exercises;

import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;

public class OptionalExercisesTest {

    @Test(expected = NullPointerException.class)
    public void optionalGetWithNullCheckTest() {
        String expected = "bla";
        assertEquals(expected, OptionalExercises.optionalGetWithNullCheck(Optional.of(expected)));

        OptionalExercises.optionalGetWithNullCheck(Optional.empty());
    }

    @Test
    public void optionalGetWithDefaultValueIfNull() {
        String expected = "bla";
        String other = "alb";

        assertEquals(expected, OptionalExercises.optionalGetWithDefaultValueIfNull(Optional.of(expected), other));
        assertEquals(other, OptionalExercises.optionalGetWithDefaultValueIfNull(Optional.empty(), other));
    }
}
