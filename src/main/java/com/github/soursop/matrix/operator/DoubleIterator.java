package com.github.soursop.matrix.operator;

import java.util.Arrays;

public class DoubleIterator extends AbstractDoubleMatrix {
    private final int height;
    private final int width;
    private final double from;

    public DoubleIterator(double from, int height, int width) {
        this.from = from;
        this.height = height;
        this.width = width;
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
    public double valueOf(int height, int width) {
        return from;
    }

    @Override
    public double valueOf(int idx) {
        return from;
    }

    @Override
    public double[] values() {
        double[] values = new double[size()];
        Arrays.fill(values, from);
        return values;
    }
}
