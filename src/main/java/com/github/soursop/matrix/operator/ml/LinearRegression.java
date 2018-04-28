package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;
import com.github.soursop.matrix.operator.DoubleOperator;
import com.github.soursop.matrix.operator.Plus;
import com.github.soursop.matrix.operator.Product;

public class LinearRegression implements Gradient {
    private static int DERIVATIVE_OF_POW = 2;
    private final DoubleMatrix input;
    private final DoubleMatrix layer;
    private final DoubleMatrix output;
    private final DoubleOperator ratio;
    private final DoubleOperator m;
    private final DoubleOperator biasUpdate;
    private final int size;
    private final double lambda;

    public LinearRegression(DoubleMatrix input, DoubleMatrix output, double lambda) {
        this.input = input;
        this.layer = Layer.of(input);
        this.output = output;
        size = input.height();
        m = DoubleOperator.of(size);
        ratio = DoubleOperator.of(lambda / size);
        biasUpdate = DoubleOperator.of(1d - lambda / size);
        this.lambda = lambda;
    }

    @Override
    public Cost gradient(DoubleMatrix theta) {
        Product hypothesis = layer.product(theta.transpose());
        DoubleMatrix error = hypothesis.minus(output).invoke();

        DoubleOperator regularization = DoubleOperator.of(lambda * theta.tail().pow(DERIVATIVE_OF_POW).sum().getValue());
        double penalty = error.pow(DERIVATIVE_OF_POW).plus(regularization).sum().getValue() / (DERIVATIVE_OF_POW * size);

        DoubleMatrix update = error.product(ratio).invoke();
        DoubleMatrix sigma = layer.transpose().product(update).transpose().invoke();
        DoubleMatrix gradient = theta.multiply(biasUpdate).minus(sigma).invoke();
        return new Cost(penalty, gradient);
    }

    @Override
    public DoubleMatrix predict(DoubleMatrix theta) {
        return layer.product(theta).invoke();
    }

    @Override
    public double cost(DoubleMatrix theta) {
        Product hypothesis = layer.product(theta.transpose());
        DoubleMatrix error = hypothesis.minus(output).invoke();

        DoubleOperator regularization = DoubleOperator.of(lambda * theta.tail().pow(DERIVATIVE_OF_POW).sum().getValue());
        return error.pow(DERIVATIVE_OF_POW).plus(regularization).sum().getValue() / (DERIVATIVE_OF_POW * size);
    }
}
