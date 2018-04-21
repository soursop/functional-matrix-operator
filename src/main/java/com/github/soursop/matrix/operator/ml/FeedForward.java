package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

public class FeedForward implements Forward {
    private final Activation function;
    private DoubleMatrix theta;
    private DoubleMatrix layer = None.DOUBLE_MATRIX;
    private DoubleMatrix z = None.DOUBLE_MATRIX;

    public FeedForward(DoubleMatrix theta, Activation function) {
        this.theta = theta;
        this.function = function;
    }

    @Override
    public DoubleMatrix gradient(DoubleMatrix sigma, double lambda, int size) {
        DoubleOperator ratio = DoubleOperator.of(lambda / size);
        DoubleOperator m = DoubleOperator.of(size);

        DoubleMatrix delta = sigma.transpose().product(layer).invoke();
        Zero regularized = new Zero(theta.tail().multiply(ratio).invoke());

        DoubleMatrix prev = this.theta;
        this.theta = delta.divide(m).plus(regularized).invoke();

        return prev;
    }

    @Override
    public DoubleMatrix forward(DoubleMatrix input) {
        layer = new Layer(input);
        z = layer.product(theta.transpose()).invoke();
        return z.apply(function.active()).asDoubleMatrix();
    }

    @Override
    public DoubleMatrix backward(DoubleMatrix sigma, DoubleMatrix z) {
        return sigma.product(theta).multiply(new Layer(z).apply(function.gradient())).invoke();
    }

    @Override
    public DoubleMatrix z() {
        return z;
    }

    class Zero extends NextDoubleMatrix {
        public Zero(DoubleMatrix origin) {
            super(new DoubleIterator(0d, origin.height(), 1), origin);
        }
    }

}
