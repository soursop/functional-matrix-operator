package com.github.soursop.matrix.operator;

abstract class AbstractOperator implements Operator {
    @Override
    public boolean isNone() {
        return None.class.isAssignableFrom(getClass());
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

    @Override
    public Plus plus(Operator other) {
        return new Plus(this, other);
    }

    @Override
    public Plus minus(Operator other) {
        return new Plus(this, other.minus());
    }

    @Override
    public Multiply divide(Operator other) {
        return new Multiply(this, other.divide());
    }

    @Override
    public Tail tail(Operator other) {
        return new Tail(this, other);
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
