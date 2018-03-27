package com.github.soursop.matrix.operator;

import java.util.Arrays;
import java.util.Collections;

abstract class AbstractOperator implements Operator {
    static Operators None = new AbstractOperators(Collections.EMPTY_LIST) {
        @Override
        public DoubleMatrix asDoubleMatrix() {
            return DoubleMatrix.NONE;
        }

        @Override
        public DoubleOperator asDoubleOperator() {
            return DoubleOperator.NONE;
        }

        @Override
        public String asSimple(int depth) {
            return "";
        }

        @Override
        public void add(Operator operator) {
        }

        @Override
        public Operators asOperators() {
            return None;
        }

    }
    ;

    @Override
    public boolean isNone() {
        return this.equals(DoubleOperator.NONE) || this.equals(DoubleMatrix.NONE) || this.equals(None);
    }

    @Override
    public Multiply multiply(Operator operator) {
        return new Multiply(Arrays.asList(this, operator));
    }

    @Override
    public Next next(Operator other) {
        return new Next(Arrays.asList(this, other));
    }

    protected StringBuilder withPadding(int depth) {
        StringBuilder builder = new StringBuilder();
        if (depth > 0) {
            builder.append("\n");
        }
        for (int i = 0; i < depth; i++) {
            builder.append(" ");
        }
        return builder;
    }
}
