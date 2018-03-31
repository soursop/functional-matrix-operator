package com.github.soursop.matrix.operator;

abstract class AbstractOperator implements Operator {
    static Operators NONE = new AbstractOperators(new Operator[0]) {
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
        public Operators asOperators() {
            return NONE;
        }

    }
    ;

    @Override
    public boolean isNone() {
        return this.equals(DoubleOperator.NONE) || this.equals(DoubleMatrix.NONE) || this.equals(NONE);
    }

    @Override
    public boolean isSome() {
        return !isNone();
    }

    @Override
    public Multiply multiply(Operator operator) {
        return new Multiply(this, operator);
    }

    @Override
    public Next next(Operator other) {
        return new Next(this, other);
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
