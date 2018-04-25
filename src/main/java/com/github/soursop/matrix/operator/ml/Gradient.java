package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

public class Gradient implements Derivative<DoubleMatrix> {
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
    public Assessed<DoubleMatrix> init(DoubleMatrix theta) {
        return new Cost(0, theta);
    }

    @Override
    public Assessed<DoubleMatrix> gradient(Assessed<DoubleMatrix> cost) {
        DoubleMatrix theta = cost.theta();
        Product hypothesis = input.product(theta);
        Plus error = hypothesis.minus(output);

        cost.cost(error.pow(DERIVATIVE_OF_POW).avg().getValue() / DERIVATIVE_OF_POW);

        Product update = error.product(ratio);
        cost.theta(theta.minus(input.transpose().product(update)).invoke());

        return cost;
    }

    @Override
    public double cost(DoubleMatrix theta) {
        Product hypothesis = input.product(theta);
        Plus error = hypothesis.minus(output);

        return error.pow(DERIVATIVE_OF_POW).avg().getValue() / DERIVATIVE_OF_POW;
    }

    public static class Cost implements Assessed<DoubleMatrix> {
        private double cost;
        private DoubleMatrix theta;

        private Cost(double cost, DoubleMatrix theta) {
            this.cost = cost;
            this.theta = theta;
        }

        @Override
        public void cost(double cost) {
            this.cost = cost;
        }

        @Override
        public void theta(DoubleMatrix theta) {
            this.theta = theta;
        }

        @Override
        public double cost() {
            return cost;
        }

        @Override
        public DoubleMatrix theta() {
            return theta;
        }
    }
}
