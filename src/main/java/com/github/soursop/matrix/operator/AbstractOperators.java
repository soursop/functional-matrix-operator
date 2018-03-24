package com.github.soursop.matrix.operator;

import java.util.Collections;
import java.util.List;

abstract class AbstractOperators extends AbstractOperator implements Operators {
    private final List<Operator> operators;
    static Operators None = new AbstractOperators(Collections.EMPTY_LIST) {
        @Override
        public Operator apply() {
            return AbstractOperator.None;
        }

        @Override
        public <T extends Operator> T apply(T prev) {
            return prev;
        }
    }
    ;

    protected AbstractOperators(List<Operator> operators) {
        this.operators = operators;
    }

    @Override
    public List<Operator> getOperators() {
        return operators;
    }

    @Override
    public Operators asOperators() {
        return this;
    }
}
