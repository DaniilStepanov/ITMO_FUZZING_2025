package org.itmo.fuzzing.lect2.instrumentation;

public class Triple<T, S, R> {
    public T first;
    public S second;
    public R third;

    public Triple(T first, S second, R third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static <T, S, R> Triple<T, S, R> of(T first, S second, R third) {
        return new Triple<>(first, second, third);
    }
}