package com.github.soursop.matrix.operator;

public class Multiply extends MatrixOperators {
    protected Multiply(Operator... operators) {
        super(operators);
    }

    @Override
    protected Sign sign() {
        return Sign.MULTIPLY;
    }
}
