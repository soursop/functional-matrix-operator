package com.github.soursop.matrix.operator;

import java.util.List;

public interface Operators extends Operator {
    List<Operator> getOperators();
    void add(Operator operator);
    <T extends Operator> T invoke(Operator prev);
}
