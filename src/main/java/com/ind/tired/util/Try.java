package com.ind.tired.util;


public interface Try<T> {
    <R, E extends Throwable> Try<R> map(CheckedFunction<T, R, E> f);
    <E extends Throwable> Try<T> recover(CheckedFunction<Throwable, T, E> f);
    T get();

    @SuppressWarnings("unchecked")
    static <T, E extends Throwable> Try<T> doing(CheckedSupplier<T, E> supplier) {
        try {
            return new Success<>(supplier.get());
        } catch (Throwable e) {
            return Failure.of(e);
        }
    }

    @SuppressWarnings("unchecked")
    static <E extends Throwable> Try<Void> doing(CheckedProcedure<E> procedure) {
        try {
            procedure.execute();
            return new Success<>(null);
        } catch (Throwable e) {
            return Failure.of(e);
        }
    }

    @SuppressWarnings("unchecked")
    static <T, R, E extends Throwable> Try<R> applying(CheckedFunction<T, R, E> f, T value) {
        try {
            return new Success<>(f.apply(value));
        } catch (Throwable e) {
            return Failure.of(e);
        }
    }

    <E extends Throwable> Either<E, T> asEither();

    boolean isFailure();

    <E extends Throwable> void fail() throws E;
}
