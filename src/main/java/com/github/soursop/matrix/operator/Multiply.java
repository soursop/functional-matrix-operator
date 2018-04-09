package com.github.soursop.matrix.operator;

public class Multiply extends AbstractOperators {
    protected Multiply(Operator... operators) {
        super(Calculation.MULTIPLY, operators);
    }
}