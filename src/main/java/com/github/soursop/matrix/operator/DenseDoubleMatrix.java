package com.github.soursop.matrix.operator;

import java.util.Iterator;

public class DenseDoubleMatrix extends DoubleMatrix implements Matrix {
    private final double[] values;
    private final int height;
    private final int width;

    public DenseDoubleMatrix(double[] values) {
        this(1, values);
    }

    public DenseDoubleMatrix(int width, double[] values) {
        this(Assert.assertHeight(values.length, width), width, values);
    }

    DenseDoubleMatrix(int height, int width, double[] values) {
        this.height = height;
        this.width = width;
        this.values = values;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public double[] values() {
        return values;
    }

    @Override
    public DoubleMatrix transpose() {
        return new DenseDoubleTransposeMatrix(this);
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
    public DoubleMatrix asDoubleMatrix() {
        return this;
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

    private static class DenseDoubleTransposeMatrix extends DoubleTranspose implements Matrix {
        private DenseDoubleMatrix origin;

        protected DenseDoubleTransposeMatrix(DenseDoubleMatrix origin) {
            this.origin = origin;
        }

        @Override
        public DenseDoubleMatrix transpose() {
            return origin;
        }

        @Override
        public double[] values() {
            double[] doubles = new double[origin.size()];
            for (int i = 0; i < doubles.length; i++) {
                doubles[i] = valueOf(i);
            }
            return doubles;
        }

        @Override
        protected Matrix origin() {
            return origin;
        }

        @Override
        public Operators asOperators() {
            return None;
        }

        @Override
        public DoubleMatrix asDoubleMatrix() {
            return this;
        }

    }

}
