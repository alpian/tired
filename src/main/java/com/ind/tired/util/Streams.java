package com.ind.tired.util;

import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Streams {
    public static <T,U> List<U> map(List<T> list, Function<T, U> f) {
        return list.stream().map(f).collect(toList());
    }

    public static <T,U> List<List<T>> groupBy(List<T> list, Function<T, U> f) {
        return collectToList(list.stream().collect(toMap(
                f,
                ImmutableList::of,
                (BinaryOperator<List<T>>) Streams::concatLists))
                .values());
    }

    public static <T> Optional<T> first(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().filter(predicate).findFirst();
    }

    public static <T> List<List<T>> collectToList(Collection<List<T>> values) {
        return values.stream().collect(Collectors.toList());
    }

    public static <T> List<T> concatLists(List<T> a, List<T> b) {
        return Stream.concat(a.stream(), b.stream()).collect(toList());
    }
}
