package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

public class FeedForward implements Forward {
    private final Activation function;
    private DoubleMatrix layer = None.DOUBLE_MATRIX;
    private DoubleMatrix z = None.DOUBLE_MATRIX;

    public FeedForward(Activation function) {
        this.function = function;
    }

    @Override
    public DoubleMatrix forward(DoubleMatrix theta, DoubleMatrix input) {
        layer = Layer.of(input);
        z = layer.product(theta.transpose()).invoke();
        return z.apply(function.active()).asDoubleMatrix();
    }

    @Override
    public DoubleMatrix backward(DoubleMatrix theta, DoubleMatrix sigma, DoubleMatrix z) {
        DoubleMatrix layer = Layer.of(z).apply(function.gradient());
        return sigma.product(theta).multiply(layer).invoke();
    }

    @Override
    public DoubleMatrix gradient(DoubleMatrix theta, DoubleMatrix sigma, double lambda, int size) {
        DoubleOperator ratio = DoubleOperator.of(lambda / size);
        DoubleOperator m = DoubleOperator.of(size);

        DoubleMatrix delta = sigma.transpose().product(layer).invoke();
        Zero regularized = Zero.of(theta.tail().multiply(ratio).invoke());

        return delta.divide(m).plus(regularized).invoke();
    }

    @Override
    public double penalty(DoubleMatrix theta) {
        return theta.tail().pow(2).sum().getValue();
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
