package com.github.soursop.matrix.operator;

public class Next extends AbstractOperators implements Sign.Next {
    protected Next(Operator ... operators) {
        super(with, operators);
    }
}
