package com.github.soursop.matrix.operator;

public interface Operators extends Operator, Iterable<Operator> {
    Operator[] getOperators();
    <T extends Operator> T invoke(Operator prev);
}
