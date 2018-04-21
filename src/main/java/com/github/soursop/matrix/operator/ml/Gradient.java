package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

public class Gradient extends Cost implements Derivative {
    private final DoubleOperator ratio;

    public Gradient(DoubleMatrix input, DoubleMatrix output, double alpha) {
        super(input, output);
        ratio = DoubleOperator.of(alpha / input.height());
    }

    @Override
    public DoubleMatrix gradient(DoubleMatrix theta) {
        Product hypothesis = input.product(theta);
        Plus error = hypothesis.minus(output);
        Product update = error.product(ratio);
        return theta.minus(input.transpose().product(update)).invoke();
    }

}
