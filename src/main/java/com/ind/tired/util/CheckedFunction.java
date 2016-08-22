package com.ind.tired.util;


import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunction<T, R, E extends Throwable> {
    R apply(T t) throws E;

    static <T, R, E extends Throwable> CheckedFunction<T,R,E> wrap(Function<T, R> inner) {
        return inner::apply;
    }
}
