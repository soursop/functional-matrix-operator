package com.github.soursop.matrix.operator;


public interface Operator extends Printable {
    Operators asOperators();
    DoubleMatrix asDoubleMatrix();
    DoubleOperator asDoubleOperator();
    Multiply multiply(Operator other);
    Next next(Operator other);
    boolean isNone();
}
