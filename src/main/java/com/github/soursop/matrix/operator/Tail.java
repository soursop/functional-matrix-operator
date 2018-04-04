package com.github.soursop.matrix.operator;


public class Tail extends AbstractOperators {
    protected Tail(Operator ... operators) {
        super(Conjunction.TAIL, operators);
    }
}
