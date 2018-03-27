package com.github.soursop.matrix.operator;

public interface Function<T, R> {
    R apply(T t);
}
