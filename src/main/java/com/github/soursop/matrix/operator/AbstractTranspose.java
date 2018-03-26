package com.github.soursop.matrix.operator;

abstract class AbstractTranspose extends AbstractMatrix {
    protected abstract Matrix origin();

    @Override
    public double valueOf(int height, int width) {
        return origin().valueOf(width * origin().width() + height);
    }

    @Override
    public int height() {
        return origin().width();
    }

    @Override
    public int width() {
        return origin().height();
    }

    @Override
    public double valueOf(int idx) {
        int w = idx / origin().height();
        int h = idx % origin().height();
        return origin().valueOf(origin().width() * h + w);
    }
}
