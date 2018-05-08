package com.example.exercises;


import javax.annotation.Nullable;
import java.util.Optional;

public class OptionalExercises {

    public static Object getWithNullCheck(@Nullable Object object) throws NullPointerException {
        if (object == null) {
            throw new NullPointerException();
        }

        return object;
    }

    public static Object optionalGetWithNullCheck(Optional<Object> object) throws NullPointerException {
        return object.orElseThrow(() -> new NullPointerException());
    }

    public static Object getWithDefaultValueIfNull(@Nullable Object object, Object other) {
        if (object == null) {
            return other;
        }

        return object;
    }

    public static Object optionalGetWithDefaultValueIfNull(Optional<Object> object, Object other) {
        return object.orElse(other);
    }
}
