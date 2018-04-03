package com.github.soursop.matrix.operator;

/**
 * @author soursop
 * @created 2018. 4. 3.
 */
public class DivideDoubleMatrix<T extends DoubleMatrix> extends DoubleMatrix {
    private final T origin;
    DivideDoubleMatrix(T origin) {
        this.origin = origin;
    }

    @Override
    public int height() {
        return origin.height();
    }

    @Override
    public int width() {
        return origin.height();
    }

    @Override
    public double valueOf(int height, int width) {
        return 1 / origin.valueOf(height, width);
    }

    @Override
    public Matrix transpose() {
        return new DoubleMatrixTranspose<>(this);
    }

    @Override
    public DoubleMatrix head() {
        return origin.head().isNone()? origin.head() : new DivideDoubleMatrix<>(origin.head());
    }

    @Override
    public DoubleMatrix tail() {
        return origin.tail().isNone()? origin.tail() : new DivideDoubleMatrix<>(origin.tail());
    }

    @Override
    public DoubleMatrix divide() {
        return origin;
    }
}
