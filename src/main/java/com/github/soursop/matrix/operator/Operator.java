package com.github.soursop.matrix.operator;

import java.io.Serializable;

public interface Operator extends Printable, Serializable {
    Operators asOperators();
    DoubleMatrix asDoubleMatrix();
    DoubleOperator asDoubleOperator();
    Multiply multiply(Operator other);
    Next next(Operator other);
    Plus plus(Operator other);
    Plus minus(Operator other);
    Multiply divide(Operator other);
    Tail tail(Operator other);
    Operator minus();
    Operator divide();
    boolean isNone();
    boolean isSome();
}
