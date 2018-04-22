package com.github.soursop.matrix.operator;

class LazyDoubleMatrix<T extends DoubleMatrix> extends AbstractDoubleMatrix {
    protected final T origin;
    private final Function function;

    LazyDoubleMatrix(Function function, T origin) {
        this.origin = origin;
        this.function = function;
    }

    @Override
    public int height() {
        return origin.height();
    }

    @Override
    public int width() {
        return origin.width();
    }

    @Override
    public double valueOf(int height, int width) {
        return function.apply(origin.valueOf(height, width));
    }

    @Override
    public double valueOf(int idx) {
        return function.apply(origin.valueOf(idx));
    }

    @Override
    public DoubleMatrix head() {
        return origin.head().isNone()? origin.head() : create(origin.head());
    }

    @Override
    public DoubleMatrix tail() {
        return origin.tail().isNone()? origin.tail() : create(origin.tail());
    }

    @Override
    public DoubleMatrix last() {
        return origin.last().isNone()? origin.last() : create(origin.last());
    }

    protected LazyDoubleMatrix create(DoubleMatrix origin) {
        return new LazyDoubleMatrix(function, origin);
    }
}
