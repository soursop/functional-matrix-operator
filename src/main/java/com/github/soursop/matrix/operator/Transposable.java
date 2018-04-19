package com.github.soursop.matrix.operator;

public interface Transposable<T extends Operator> extends Reducible, Operator {
    T transpose();
}
