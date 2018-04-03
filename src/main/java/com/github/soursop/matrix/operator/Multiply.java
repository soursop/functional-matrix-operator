package com.github.soursop.matrix.operator;

public class Multiply extends AbstractSignOperators {
    protected Multiply(Operator... operators) {
        super(Sign.MULTIPLY, operators);
    }
}