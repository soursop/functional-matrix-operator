package com.github.soursop.matrix.operator;

import java.util.Arrays;

abstract class DoubleMatrix extends AbstractOperator implements Matrix {
    static final DoubleMatrix NONE = new DenseDoubleMatrix(0, 0, new double[0]);

    @Override
    public CharSequence asSimple(int depth) {
        return new StringBuilder().append(height()).append(":").append(width());
    }

    @Override
    public DoubleOperator asDoubleOperator() {
        return DoubleOperator.NONE;
    }

    @Override
    public Operators asOperators() {
        return None;
    }

    @Override
    public Next next(Operator other) {
        return new Next(Arrays.asList(this, other));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int h = 0; h < height(); h++) {
            for (int w = 0; w < width(); w++) {
                builder.append(valueOf(h, w));
                if (w + 1 < width()) {
                    builder.append(",");
                }
            }
            if (h + 1 < height()) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public int size() {
        return height() * width();
    }

    public abstract double[] values();
}
