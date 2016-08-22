package com.ind.tired.util;


public class Pair<A, B> {
    public final A a;
    public final B b;

    private Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public static <A, B> Pair<A, B> of(A a, B b) {
        return new Pair<>(a, b);
    }

    public static <A, B> Pair<A, B> pair(A a, B b) {
        return of(a, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        return a.equals(pair.a) && b.equals(pair.b);

    }

    @Override
    public int hashCode() {
        int result = a.hashCode();
        result = 31 * result + b.hashCode();
        return result;
    }

    public <C> Pair<A, C> copyA(C c) {
        return of(a, c);
    }

    public A a() {
        return a;
    }

    public B b() {
        return b;
    }
}
