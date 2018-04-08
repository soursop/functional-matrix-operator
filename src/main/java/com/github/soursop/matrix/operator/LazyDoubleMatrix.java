package com.github.soursop.matrix.operator;

class LazyDoubleMatrix<T extends DoubleMatrix> extends AbstractDoubleMatrix {
    protected final T origin;
    private final Function function;
    private final String sign;
    LazyDoubleMatrix(Function function, T origin) {
        this("func", function, origin);
    }
    LazyDoubleMatrix(String sign, Function function, T origin) {
        this.origin = origin;
        this.function = function;
        this.sign = sign;
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
    public DoubleMatrix transpose() {
        return new DoubleMatrixTranspose<>(this);
    }

    @Override
    public DoubleMatrix head() {
        return origin.head().isNone()? origin.head() : new LazyDoubleMatrix<>(sign, function, origin.head());
    }

    @Override
    public DoubleMatrix tail() {
        return origin.tail().isNone()? origin.tail() : new LazyDoubleMatrix<>(sign, function, origin.tail());
    }

    @Override
    public DoubleMatrix last() {
        return origin.last().isNone()? origin.last() : new LazyDoubleMatrix<>(sign, function, origin.last());
    }

    @Override
    protected CharSequence _asSimple(int depth) {
        withPadding();
        append(sign);
        append(height());
        append(":");
        append(width());
        return getBuilder();
    }
}
