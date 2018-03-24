package com.github.soursop.matrix.operator;

import java.util.Collections;

public interface Operator {
    <T extends Operator> T apply();
    Operators asOperators();
}
