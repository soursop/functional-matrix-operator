package com.github.soursop.matrix.operator;

public interface Operators extends Operator, Iterable<Operator>, Reducible {
    Operator[] getOperators();
    DoubleMatrix invoke();
    DoubleMatrix invoke(Operator prev);
}
