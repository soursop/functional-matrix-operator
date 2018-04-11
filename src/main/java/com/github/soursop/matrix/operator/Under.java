package com.github.soursop.matrix.operator;


class Under extends WithOperators implements Sign.Under {
    protected Under(Operator ... operators) {
        super(with, operators);
    }
}
