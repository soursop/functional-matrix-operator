package com.github.soursop.matrix.operator;

public interface Operators extends Operator, Iterable<Operator> {
    Operator[] getOperators();
    DoubleMatrix invoke();
    DoubleMatrix invoke(Operator prev);
}
