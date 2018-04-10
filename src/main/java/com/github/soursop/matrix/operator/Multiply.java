package com.github.soursop.matrix.operator;

public class Multiply extends AbstractOperators implements Sign.Multiply {
    protected Multiply(Operator... operators) {
        super(with, operators);
    }
}