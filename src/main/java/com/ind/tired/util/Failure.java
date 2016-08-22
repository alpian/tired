package com.ind.tired.util;


public class Failure<V, T extends Throwable> implements Try<V> {
    private final T exception;

    public Failure(T exception) {
        this.exception = exception;
    }

    @Override
    public <R, E extends Throwable> Try<R> map(CheckedFunction<V, R, E> f) {
        return new Failure<>(exception);
    }

    @Override
    public <E extends Throwable> Try<V> recover(CheckedFunction<Throwable, V, E> f) {
        return Try.applying(f, exception);
    }

    @Override
    public V get() {
        throw new RuntimeException(exception);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends Throwable> Either<E, V> asEither() {
        return Either.Left.of((E)exception);
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends Throwable> void fail() throws E {
        throw (E)exception;
    }

    @SuppressWarnings("unchecked")
    public static <R, E extends Exception> Try<R> of(Throwable e) {
        return new Failure<>((E)e);
    }
}
