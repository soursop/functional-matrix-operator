package com.github.soursop.matrix.operator;

abstract class AbstractOperator implements Operator {
    private StringBuilder builder;
    private int depth = 0;

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

    @Override
    public String toString() {
        return asSimple(0).toString();
    }

    protected void initBuilder(int depth) {
        this.depth = depth;
        this.builder = new StringBuilder();
    }

    protected void withPadding() {
        for (int i = 0; i < depth; i++) {
            builder.append(" ");
        }
    }

    protected void append(double value) {
        builder.append(value);
    }

    protected void append(int value) {
        builder.append(value);
    }

    protected void append(CharSequence value) {
        builder.append(value);
    }

    protected void withPadding(int value) {
        withPadding();
        builder.append(value);
    }

    protected void withPadding(CharSequence value) {
        withPadding();
        builder.append(value);
    }

    protected void withLine(CharSequence value) {
        withPadding(value);
        builder.append("\n");
    }

    protected StringBuilder getBuilder() {
        return builder;
    }
}
