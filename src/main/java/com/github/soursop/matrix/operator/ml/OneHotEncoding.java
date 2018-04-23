package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

/**
 * @author soursop
 * @created 2018. 4. 19.
 */
public class OneHotEncoding extends DenseDoubleMatrix {

    public static OneHotEncoding of(int size, double[] values) {
        return of(size, DenseDoubleMatrix.of(values));
    }

    public static OneHotEncoding of(int size, DoubleMatrix one) {
        int height = one.size();
        double[] values = new double[height * size];
        for (int h = 0; h < height; h++) {
            double found = one.valueOf(h);
            for (int w = 0; w < size; w++) {
                double v = w == found - 1 ? 1d : 0d;
                values[indexOf(h, w, height)] = v;
            }
        }
        return new OneHotEncoding(height, size, values);
    }

    private OneHotEncoding(int height, int width, double[] values) {
        super(height, width, values);
    }

}
