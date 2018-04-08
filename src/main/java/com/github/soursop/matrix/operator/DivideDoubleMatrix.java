package com.github.soursop.matrix.operator;

/**
 * @author soursop
 * @created 2018. 4. 3.
 */
public class DivideDoubleMatrix<T extends DoubleMatrix> extends LazyDoubleMatrix<T> {
    private static Function function = new Function() {
        @Override
        public double apply(double v) {
            return 1 / v;
        }
    }
    ;

    @Override
    public DoubleMatrix divide() {
        return origin;
    }

    DivideDoubleMatrix(T origin) {
        super("1/", function, origin);
    }
}
