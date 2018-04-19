package com.github.soursop.matrix.operator;

public interface Operators extends Transposable<Operators>, Iterable<Operator> {
    Operator[] getOperators();
    DoubleMatrix invoke();
    DoubleMatrix invoke(Operator prev);
}
