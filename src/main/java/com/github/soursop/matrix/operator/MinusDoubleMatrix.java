package com.github.soursop.matrix.operator;

/**
 * @author soursop
 * @created 2018. 4. 3.
 */
public class MinusDoubleMatrix<T extends DoubleMatrix> extends LazyDoubleMatrix<T> implements Sign.Minus {
    MinusDoubleMatrix(T origin) {
        super(function, origin);
    }

    @Override
    public DoubleMatrix minus() {
        return origin;
    }

    @Override
    protected MinusDoubleMatrix create(DoubleMatrix origin) {
        return new MinusDoubleMatrix<>(origin);
    }
}
