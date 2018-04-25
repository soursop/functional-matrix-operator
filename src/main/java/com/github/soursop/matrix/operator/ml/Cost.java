package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;
import com.github.soursop.matrix.operator.Plus;
import com.github.soursop.matrix.operator.Product;

public class Cost implements Assessed<DoubleMatrix> {
    protected static int DERIVATIVE_OF_POW = 2;
    protected final DoubleMatrix input;
    protected final DoubleMatrix output;

    public Cost(DoubleMatrix input, DoubleMatrix output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public double cost(DoubleMatrix theta) {
        Product hypothesis = input.product(theta);
        Plus error = hypothesis.minus(output);
        return error.pow(DERIVATIVE_OF_POW).avg().getValue() / DERIVATIVE_OF_POW;
    }
}
