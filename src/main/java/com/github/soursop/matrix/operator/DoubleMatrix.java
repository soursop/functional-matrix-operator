package com.github.soursop.matrix.operator;

class DoubleMatrix implements Matrix {
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

    @Override
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

    @Override
    public Matrix multiply(Matrix matrix) {
        double[] values = new double[height() * matrix.width()];
        for (int h = 0; h < height(); h++) {
            for (int s = 0; s < matrix.width(); s++) {
                double v = 0;
                for (int w = 0; w < width(); w++) {
                    v = v + valueOf(h, w) * matrix.valueOf(w, s);
                }
                values[h * matrix.width() + s] = v;
            }
        }
        return new DoubleMatrix(matrix.width(), values);
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
}
