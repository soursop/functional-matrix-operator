package com.github.soursop.matrix.operator;

public class Next extends AbstractOperators {
    protected Next(Operator ... operators) {
        super(Conjunction.NEXT, operators);
    }
}
