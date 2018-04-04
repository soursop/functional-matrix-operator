package com.github.soursop.matrix.operator;

class DoubleMatrixTranspose<T extends DoubleMatrix> extends AbstractDoubleMatrix {
    private final T origin;
    DoubleMatrixTranspose(T origin) {
        this.origin = origin;
    }

    @Override
    public double valueOf(int height, int width) {
        return origin.valueOf(width, height);
    }

    @Override
    public int height() {
        return origin.width();
    }

    @Override
    public int width() {
        return origin.height();
    }

    @Override
    public double valueOf(int idx) {
        int w = idx / origin.height();
        int h = idx % origin.height();
        return origin.valueOf(origin.width() * h + w);
    }

    @Override
    public T transpose() {
        return origin;
    }

    @Override
    public DoubleMatrix head() {
        return origin.head();
    }

    @Override
    public DoubleMatrix tail() {
        return origin.tail();
    }
}
