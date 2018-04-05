package com.github.soursop.matrix.operator;


public class Under extends AbstractOperators {
    protected Under(Operator ... operators) {
        super(Conjunction.UNDER, operators);
    }
}
