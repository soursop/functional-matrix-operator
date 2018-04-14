package com.github.soursop.matrix.operator;

public class MutableDoubleMatrix extends AbstractDoubleMatrix {
    private double[] values;
    private int height;
    private int width;

    public MutableDoubleMatrix(double... values) {
        this(1, values);
    }

    public MutableDoubleMatrix(int width, double... values) {
        this(Assert.assertHeight(values.length, width), width, values);
    }

    public void set(double[] values) {
        this.values = values;
    }

    public void set(int width, double... values) {
        height = Assert.assertHeight(values.length, width);
        this.width = width;
        this.values = values;
    }

    MutableDoubleMatrix(int height, int width, double... values) {
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

}
