package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

public class Regression implements Gradient {
    private static int DERIVATIVE_OF_POW = 2;
    private final DoubleMatrix layer;
    private final DoubleMatrix output;
    private final Activation activation;
    private final int size;
    private final double lambda;

    public Regression(DoubleMatrix input, DoubleMatrix output, Activation activation, double lambda) {
        this.layer = Layer.of(input);
        this.output = output;
        size = input.height();
        this.activation = activation;
        this.lambda = lambda;
    }

    @Override
    public Cost gradient(DoubleMatrix theta) {
        DoubleMatrix hypothesis = layer.product(theta.transpose()).apply(activation.active()).invoke();
        DoubleMatrix error = hypothesis.minus(output).invoke();

        Zero zero = Zero.of(theta.tail());
        double p = zero.pow(DERIVATIVE_OF_POW).sum().getValue();
        double loss = activation.loss().loss(output, hypothesis) + lambda * p / (DERIVATIVE_OF_POW * size);

        DoubleMatrix delta = error.transpose().product(layer).invoke();
        Multiply regularized = DoubleOperator.of(lambda).multiply(zero);
        DoubleMatrix gradient = delta.plus(regularized).divide(DoubleOperator.of(size)).invoke();

        return new Cost(loss, gradient);
    }

    @Override
    public DoubleMatrix predict(DoubleMatrix theta) {
        return layer.product(theta).invoke();
    }

    @Override
    public double cost(DoubleMatrix theta) {
        DoubleMatrix hypothesis = layer.product(theta.transpose()).apply(activation.active()).invoke();

        double p = theta.tail().pow(DERIVATIVE_OF_POW).sum().getValue();
        return activation.loss().loss(output, hypothesis) + lambda * p / (DERIVATIVE_OF_POW * size);
    }
}
