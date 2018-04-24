package com.github.soursop.matrix.operator;



public class DenseDoubleMatrix extends AbstractDoubleMatrix {
    private final double[] values;
    private final int height;
    private final int width;

    public static DenseDoubleMatrix of(double... values) {
        return of(1, values);
    }

    public static DenseDoubleMatrix of(int width, double... values) {
        int height = Assert.assertHeight(values.length, width);
        return new DenseDoubleMatrix(height, width, values);
    }

    protected static double[] asVector(int height, int width, double[] values) {
        if (width == 1) {
            return values;
        }
        double[] doubles = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            doubles[i] = values[(i % height) * width + i / height];
        }
        return doubles;
    }

    protected DenseDoubleMatrix(int height, int width, double... values) {
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
    public double valueOf(int idx) {
        return values[idx];
    }

    @Override
    public double valueOf(int height, int width) {
        return values[indexOf(height, width, this.width)];
    }

    String productAsDebug(DoubleMatrix matrix) {
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

    public static class WithValues {
        public final int height;
        public final int width;
        public final double[] values;
        WithValues(int width, double[] values) {
            this.height = Assert.assertHeight(values.length, width);
            this.width = width;
            this.values = values;
        }
    }
}
