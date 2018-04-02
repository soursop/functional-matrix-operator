package com.github.soursop.matrix.operator;

public class DoubleOperator extends AbstractOperator {
    private final double value;

    public DoubleOperator(double value) {
        this.value = value;
    }

    public static DoubleOperator of(double value) {
        return new DoubleOperator(value);
    }

    double getValue() {
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
    public CharSequence asSimple(int depth) {
        return withPadding(depth).append(value);
    }

    public DoubleMatrix toIterator(int height, int width) {
        return isNone()? None.DOUBLE_MATRIX : new DoubleIterator(value, height, width);
    }

    @Override
    public Operators asOperators() {
        return None.OPERATORS;
    }
}
