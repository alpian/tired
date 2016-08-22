package com.ind.tired.util;


@FunctionalInterface
public interface CheckedSupplier<T, E extends Throwable> {
    T get() throws E;
}
