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
        Zero regularized = Zero.of(theta.tail().multiply(ratio).invoke());

        DoubleMatrix prev = this.theta;
        this.theta = delta.divide(m).plus(regularized).invoke();

        return prev;
    }

    @Override
    public DoubleMatrix forward(DoubleMatrix input) {
        layer = Layer.of(input);
        z = layer.product(theta.transpose()).invoke();
        return z.apply(function.active()).asDoubleMatrix();
    }

    @Override
    public DoubleMatrix backward(DoubleMatrix sigma, DoubleMatrix z) {
        return sigma.product(theta).multiply(Layer.of(z).apply(function.gradient())).invoke();
    }

    @Override
    public DoubleMatrix theta() {
        return theta;
    }

    @Override
    public double penalty() {
        return SumOperator.of(theta.tail().pow(2)).getValue();
    }

    @Override
    public DoubleMatrix z() {
        return z;
    }

    static class Zero extends NextDoubleMatrix {
        private Zero(int height, int width, double[] values) {
            super(height, width, values);
        }

        public static Zero of(DoubleMatrix origin) {
            WithValues matrix = combine(new DoubleIterator(0d, origin.height(), 1), origin);
            return new Zero(matrix.height, matrix.width, matrix.values);
        }
    }

}
