package com.github.soursop.matrix.operator;

/**
 * @author soursop
 * @created 2018. 4. 3.
 */
public class MinusDoubleMatrix<T extends DoubleMatrix> extends DoubleMatrix {
    private final T origin;
    MinusDoubleMatrix(T origin) {
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
        return -origin.valueOf(height, width);
    }

    @Override
    public Matrix transpose() {
        return new DoubleMatrixTranspose<>(this);
    }

    @Override
    public DoubleMatrix head() {
        return origin.head().isNone()? origin.head() : new MinusDoubleMatrix<>(origin.head());
    }

    @Override
    public DoubleMatrix tail() {
        return origin.tail().isNone()? origin.tail() : new MinusDoubleMatrix<>(origin.tail());
    }

    @Override
    public DoubleMatrix minus() {
        return origin;
    }
}
