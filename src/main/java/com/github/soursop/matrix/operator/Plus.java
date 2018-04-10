package com.github.soursop.matrix.operator;


public class Plus extends AbstractOperators implements Sign.Plus {
    protected Plus(Operator... operators) {
        super(with, operators);
    }
}
