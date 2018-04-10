package com.github.soursop.matrix.operator;

/**
 * @author soursop
 * @created 2018. 4. 3.
 */
public class DivideDoubleMatrix<T extends DoubleMatrix> extends LazyDoubleMatrix<T> implements Sign.Divide {
    DivideDoubleMatrix(T origin) {
        super(function, origin);
    }

    @Override
    public DoubleMatrix divide() {
        return origin;
    }

    @Override
    protected DivideDoubleMatrix create(DoubleMatrix origin) {
        return new DivideDoubleMatrix(origin);
    }
}
