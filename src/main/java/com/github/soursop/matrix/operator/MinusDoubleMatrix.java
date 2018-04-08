package com.github.soursop.matrix.operator;

/**
 * @author soursop
 * @created 2018. 4. 3.
 */
public class MinusDoubleMatrix<T extends DoubleMatrix> extends LazyDoubleMatrix<T> {
    private static Function function = new Function() {
        @Override
        public double apply(double v) {
            return -v;
        }
    }
    ;

    MinusDoubleMatrix(T origin) {
        super("-", function, origin);
    }

    @Override
    public DoubleMatrix minus() {
        return origin;
    }
}
