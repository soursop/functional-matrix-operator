package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

/**
 * @author soursop
 * @created 2018. 4. 19.
 */
public class OneHotEncoding extends DenseDoubleMatrix {

    public static OneHotEncoding of(int size, double[] values) {
        return of(size, new DenseDoubleMatrix(values));
    }

    public static OneHotEncoding of(int size, DoubleMatrix one) {
        double[] values = new double[one.size() * size];
        for (int h = 0; h < one.height(); h++) {
            double found = one.valueOf(h);
            for (int w = 0; w < size; w++) {
                double v = w == found - 1 ? 1d : 0d;
                values[h * size + w] = v;
            }
        }
        return new OneHotEncoding(size, values);
    }

    private OneHotEncoding(int width, double[] values) {
        super(width, values);
    }

}
