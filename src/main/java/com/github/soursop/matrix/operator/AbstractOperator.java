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
    public <T extends Operator> T as(T t) {
        return (T) this;
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
    public Multiply divide(DoubleOperator other) {
        return new Multiply(this, other.divide());
    }

    @Override
    public Under under(Operator other) {
        return new Under(this, other);
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
