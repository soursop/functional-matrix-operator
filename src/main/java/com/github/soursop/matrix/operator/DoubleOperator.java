package com.github.soursop.matrix.operator;

public class DoubleOperator extends AbstractOperator {
    public static DoubleOperator ONE = new DoubleOperator(1d);
    public static DoubleOperator ZERO = new DoubleOperator(0d);

    private final double value;

    public DoubleOperator(double value) {
        this.value = value;
    }

    public static DoubleOperator of(double value) {
        return new DoubleOperator(value);
    }

    public double getValue() {
        return value;
    }

    @Override
    public DoubleMatrix asDoubleMatrix() {
        return None.DOUBLE_MATRIX;
    }

    @Override
    public DoubleOperator asDoubleOperator() {
        return this;
    }

    @Override
    public DoubleOperator minus() {
        return new DoubleOperator(-value);
    }

    @Override
    public DoubleOperator divide() {
        return new DoubleOperator(1 / value);
    }

    @Override
    public Operator pow(int pow) {
        return new DoubleOperator(Math.pow(value, pow));
    }

    @Override
    public Operator apply(Function function) {
        return new DoubleOperator(function.apply(value));
    }

    @Override
    public CharSequence asSimple(int depth) {
        initBuilder(depth);
        withPadding();
        append(String.format("%.4f", value));
        return getBuilder();
    }

    public DoubleMatrix toIterator(int height, int width) {
        return isNone()? None.DOUBLE_MATRIX : new DoubleIterator(value, height, width);
    }

    @Override
    public Operators asOperators() {
        return None.OPERATORS;
    }
}
