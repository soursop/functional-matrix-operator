package com.github.soursop.matrix.operator;


public class Plus extends MatrixOperators {
    protected Plus(Operator... operators) {
        super(operators);
    }

    @Override
    protected Sign sign() {
        return Sign.PLUS;
    }
}
