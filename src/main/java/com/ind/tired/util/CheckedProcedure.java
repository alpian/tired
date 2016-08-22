package com.ind.tired.util;


@FunctionalInterface
public interface CheckedProcedure<E extends Throwable> {
    void execute() throws E;
}
