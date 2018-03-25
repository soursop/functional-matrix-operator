package com.github.soursop.matrix.operator;

import java.util.Arrays;

class DoubleMatrix extends AbstractOperator implements Matrix {
    static DoubleMatrix NONE = new DoubleMatrix(0, 0, new double[0]);
    private final int height;
    private final int width;
    private final double[] values;

    protected DoubleMatrix(int height, int width, double[] values) {
        this.values = values;
        this.height = height;
        this.width = width;
    }

    protected DoubleMatrix(int width, double[] values) {
        this(values.length / width, width, values);
    }

    protected DoubleMatrix(double[] values) {
        this(values.length, 1, values);
    }

    public double[] values() {
        return values;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int width() {
        return width;
    }

    String multiplyAsDebug(Matrix matrix) {
        StringBuilder builder = new StringBuilder();
        for (int h = 0; h < height(); h++) {
            for (int s = 0; s < matrix.width(); s++) {
                for (int w = 0; w < width(); w++) {
                    builder.append(String.format(" + %d,%d * %d,%d", h, w, w, s));
                }
                builder.append(" <-" + h * matrix.width() + ":" + s + "\n");
            }
        }
        return builder.toString();
    }

    @Override
    public Matrix transpose() {
        return new Transpose(height, width, values);
    }

    @Override
    public double valueOf(int height, int width) {
        return values[height * width() + width];
    }

    @Override
    public double valueOf(int idx) {
        return values[idx];
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
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public DoubleMatrix asDoubleMatrix() {
        return this;
    }

    @Override
    public DoubleOperator asDoubleOperator() {
        return DoubleOperator.NONE;
    }

    @Override
    public CharSequence asSimple(int depth) {
        return new StringBuilder().append(height()).append(":").append(width());
    }
}
