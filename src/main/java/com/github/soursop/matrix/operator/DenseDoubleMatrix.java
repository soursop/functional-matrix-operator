package com.github.soursop.matrix.operator;

public class DenseDoubleMatrix extends DoubleMatrix {
    private final double[] values;
    private final int height;
    private final int width;

    public DenseDoubleMatrix(double... values) {
        this(1, values);
    }

    public DenseDoubleMatrix(int width, double... values) {
        this(Assert.assertHeight(values.length, width), width, values);
    }

    DenseDoubleMatrix(int height, int width, double... values) {
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
        return new DoubleMatrixTranspose<>(this);
    }

    @Override
    public double valueOf(int height, int width) {
        return values[height * width() + width];
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
    public DoubleMatrix head() {
        return None.DOUBLE_MATRIX;
    }

    @Override
    public DoubleMatrix tail() {
        return None.DOUBLE_MATRIX;
    }
}
