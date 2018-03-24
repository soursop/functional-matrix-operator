package com.github.soursop.matrix.operator;

import java.util.Collections;

abstract class AbstractOperator implements Operator {
    static Operator None = new AbstractOperator() {
        @Override
        public AbstractOperator apply() {
            return this;
        }

        @Override
        public Operators asOperators() {
            return AbstractOperators.None;
        }
    }
    ;
}
