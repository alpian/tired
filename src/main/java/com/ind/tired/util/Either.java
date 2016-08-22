package com.ind.tired.util;


import java.util.function.Function;

public interface Either<L, R> {
    <V> Either<L, V> mapRight(Function<R, V> f);
    <V> Either<V, R> mapLeft(Function<L, V> f);

    R right();

    boolean isLeft();

    L left();


    class Right<L, R> implements Either<L, R> {
        private final R value;

        private Right(R value) {
            this.value = value;
        }

        public static <L, R> Right<L,R> of(R r) {
            return new Right<>(r);
        }

        @Override
        public <V> Either<L, V> mapRight(Function<R, V> f) {
            return of(f.apply(value));
        }

        @Override
        public <V> Either<V, R> mapLeft(Function<L, V> f) {
            return of(value);
        }

        @Override
        public R right() {
            return value;
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public L left() {
            throw new IllegalStateException("Cannot access the left of a Right Either");
        }
    }

    class Left<L, R> implements Either<L, R> {
        private final L value;

        private Left(L value) {
            this.value = value;
        }

        public static <L, R> Left<L, R> of(L l) {
            return new Left<>(l);
        }

        @Override
        public <V> Either<L, V> mapRight(Function<R, V> f) {
            return of(value);
        }

        @Override
        public <V> Either<V, R> mapLeft(Function<L, V> f) {
            return of(f.apply(value));
        }

        @Override
        public R right() {
            throw new IllegalStateException("Cannot access the right of a Left Either");
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public L left() {
            return value;
        }
    }
}
