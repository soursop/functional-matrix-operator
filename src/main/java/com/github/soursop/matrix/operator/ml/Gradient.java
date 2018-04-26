package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

public class Gradient implements Derivative {
    private static int DERIVATIVE_OF_POW = 2;
    private final DoubleMatrix input;
    private final DoubleMatrix output;
    private final DoubleOperator ratio;

    public Gradient(DoubleMatrix input, DoubleMatrix output) {
        this(input, output, 0d);
    }

    public Gradient(DoubleMatrix input, DoubleMatrix output, double alpha) {
        this.input = input;
        this.output = output;
        ratio = DoubleOperator.of(alpha / input.height());
    }

    @Override
    public Cost gradient(DoubleMatrix theta) {
        Product hypothesis = input.product(theta);
        Plus error = hypothesis.minus(output);

        double penalty = error.pow(DERIVATIVE_OF_POW).avg().getValue() / DERIVATIVE_OF_POW;

        Product update = error.product(ratio);
        DoubleMatrix gradient = theta.minus(input.transpose().product(update)).invoke();

        return new Cost(penalty, gradient);
    }

    @Override
    public double cost(DoubleMatrix theta) {
        Product hypothesis = input.product(theta);
        Plus error = hypothesis.minus(output);

        return error.pow(DERIVATIVE_OF_POW).avg().getValue() / DERIVATIVE_OF_POW;
    }
}
