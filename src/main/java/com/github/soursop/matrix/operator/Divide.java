package com.github.soursop.matrix.operator;


public class Divide extends MatrixOperators {
    public Divide(Operator... operators) {
        super(operators);
    }

    @Override
    protected Sign sign() {
        return Sign.DIVIDE;
    }
}
