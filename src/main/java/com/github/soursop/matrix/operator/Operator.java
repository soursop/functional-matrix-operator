package com.github.soursop.matrix.operator;

import java.io.Serializable;

public interface Operator extends Printable, Serializable {
    Operators asOperators();
    DoubleMatrix asDoubleMatrix();
    DoubleOperator asDoubleOperator();
    Multiply multiply(Operator other);
    Next next(Operator other);
    Plus plus(Operator other);
    Minus minus(Operator other);
    Divide divide(Operator other);
    Tail tail(Operator other);
    boolean isNone();
    boolean isSome();
}
