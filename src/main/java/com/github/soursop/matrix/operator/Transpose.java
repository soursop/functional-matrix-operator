package com.github.soursop.matrix.operator;

class Transpose extends DoubleMatrix {
    protected Transpose(int height, int width, double[] values) {
        super(height, width, values);
    }

    @Override
    public double valueOf(int height, int width) {
        return values()[width * super.width() + height];
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
    public double valueOf(int idx) {
        int w = idx / super.height();
        int h = idx % super.height();
        return values()[super.width() * h + w];
    }
}
