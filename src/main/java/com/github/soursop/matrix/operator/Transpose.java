package com.github.soursop.matrix.operator;

class Transpose extends DoubleMatrix {
    protected Transpose(int height, int width, double[] values) {
        super(height, width, values);
    }

    @Override
    public double valueOf(int height, int width) {
        return super.values()[width * super.width() + height];
    }

    @Override
    public int height() {
        return super.width();
    }

    @Override
    public int width() {
        return super.height();
    }

    @Override
    public double[] values() {
        double[] doubles = new double[super.values().length];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = valueOf(i);
        }
        return doubles;
    }

    @Override
    public double valueOf(int idx) {
        int w = idx / super.height();
        int h = idx % super.height();
        return super.values()[super.width() * h + w];
    }
}
