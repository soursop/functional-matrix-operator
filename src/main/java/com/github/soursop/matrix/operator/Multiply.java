package com.github.soursop.matrix.operator;

public class Multiply extends WithOperators implements Sign.Multiply {
    protected Multiply(Operator... operators) {
        super(with, operators);
    }
}