package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleIterator;
import com.github.soursop.matrix.operator.DoubleMatrix;
import com.github.soursop.matrix.operator.NextDoubleMatrix;

/**
 * @author soursop
 * @created 2018. 4. 19.
 */
public class Layer extends NextDoubleMatrix {
    private Layer(int height, int width, double[] values) {
        super(height, width, values);
    }

    public static Layer of(DoubleMatrix origin) {
        WithValues combine = combine(new DoubleIterator(1d, origin.height(), 1), origin);
        return new Layer(combine.height, combine.width, combine.values);
    }
}
