package com.github.soursop.matrix.operator;

import java.io.Serializable;

public interface Operator<O extends Operator> extends Printable, Serializable {
    Operators asOperators();
    DoubleMatrix asDoubleMatrix();
    DoubleOperator asDoubleOperator();
    Product product(Operator other);
    Multiply multiply(Operator other);
    Next next(Operator other);
    Plus plus(Operator other);
    Plus minus(Operator other);
    Product divide(DoubleOperator other);
    Under under(Operator other);
    O minus();
    O divide();
    O pow(int pow);
    O apply(Function function);
    DoubleOperator dot(Operator other);
    boolean isNone();
    boolean isSome();
}
