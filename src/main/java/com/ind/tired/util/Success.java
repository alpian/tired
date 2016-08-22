package com.ind.tired.util;


public class Success<T> implements Try<T> {
    private final T value;

    public Success(T value) {
        this.value = value;
    }

    @Override
    public <R, E extends Throwable> Try<R> map(CheckedFunction<T, R, E> f) {
        return applying(f, value);
    }

    @Override
    public <E extends Throwable> Try<T> recover(CheckedFunction<Throwable, T, E> f) {
        return this;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public <E extends Throwable> Either<E, T> asEither() {
        return Either.Right.of(value);
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public <E extends Throwable> void fail() throws E {

    }
}
