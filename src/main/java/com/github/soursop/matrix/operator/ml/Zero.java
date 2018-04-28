package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleIterator;
import com.github.soursop.matrix.operator.DoubleMatrix;
import com.github.soursop.matrix.operator.NextDoubleMatrix;

public class Zero extends NextDoubleMatrix {
    private Zero(int height, int width, double[] values) {
        super(height, width, values);
    }

    public static Zero of(DoubleMatrix origin) {
        WithValues matrix = combine(new DoubleIterator(0d, origin.height(), 1), origin);
        return new Zero(matrix.height, matrix.width, matrix.values);
    }
}
