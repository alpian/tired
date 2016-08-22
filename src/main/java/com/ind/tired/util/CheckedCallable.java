package com.ind.tired.util;


@FunctionalInterface
public interface CheckedCallable<T, E extends Throwable> {
    void apply(T t) throws E;
}
