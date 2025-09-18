package org.itmo.fuzzing.lect2.instrumentation;

import java.util.List;

public class Pair<T, S> {
    public T first;
    public S second;

    public Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    public S getValue() {
        return second;
    }

    public T getKey() {
        return first;
    }

    public static <T, S> Pair<T, S> of(T first, S second) {
        return new Pair<>(first, second);
    }
}
