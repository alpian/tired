package com.ind.tired.util;

import java.util.function.Consumer;
import java.util.function.Function;

public class VoidFunction {
    public static <T> Function<T, Void> fromConsumer(Consumer<T> consumer) {
        return t -> {
            consumer.accept(t);
            return null;
        };
    }

    public static <T, E extends Throwable> CheckedFunction<T, Void, E> fromCheckedConsumer(CheckedConsumer<T, E> consumer) {
        return t -> {
            consumer.accept(t);
            return null;
        };
    }
}
