package com.github.soursop.matrix.operator;


public class Plus extends WithOperators implements Sign.Plus {
    protected Plus(Operator... operators) {
        super(with, operators);
    }
}
