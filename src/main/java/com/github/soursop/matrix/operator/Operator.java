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
    Multiply divide(DoubleOperator other);
    Tail tail(Operator other);
    <T extends Operator> T as(T t);
    Operator minus();
    Operator divide();
    boolean isNone();
    boolean isSome();
}
