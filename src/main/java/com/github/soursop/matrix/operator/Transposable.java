package com.github.soursop.matrix.operator;

public interface Transposable<T extends Operator> extends Operator {
    T transpose();
}
