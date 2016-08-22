package com.ind.tired.util;


import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedConsumer<T, E extends Throwable> {
    void accept(T t) throws E;

    static <T, E extends Throwable> Consumer<T> asUnchecked(CheckedConsumer<T, E> checkedConsumer) {
        return t -> {
            try {
                checkedConsumer.accept(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}
