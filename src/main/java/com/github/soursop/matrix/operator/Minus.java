package com.github.soursop.matrix.operator;

public class Minus extends MatrixOperators {
    protected Minus(Operator... operators) {
        super(operators);
    }

    @Override
    protected Sign sign() {
        return Sign.MINUS;
    }
}
