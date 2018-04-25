package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;

/**
 * @author soursop
 * @created 2018. 4. 24.
 */
public class NeuralNetwork implements Derivative<Network> {
    private final DoubleMatrix input;
    private final DoubleMatrix output;
    private final double lambda;
    private final int size;

    public NeuralNetwork(DoubleMatrix input, DoubleMatrix output, double lambda) {
        this.input = input;
        this.output = output;
        this.lambda = lambda;
        size = input.size();
    }

    @Override
    public Assessed<Network> init(Network network) {
        return new Cost(0, network);
    }

    @Override
    public Assessed<Network> gradient(Assessed<Network> cost) {
        DoubleMatrix hypothesis = input;
        FeedForward[] forwards = cost.theta().getForwards();
        double p = 0d;
        for (FeedForward forward : forwards) {
            hypothesis = forward.forward(hypothesis);
            p += forward.penalty();
        }

        cost.cost(LossFunction.LOGISTIC.loss(output, hypothesis) + lambda * p / (2 * size));

        DoubleMatrix decent = hypothesis.minus(output).invoke();
        // decent 여기까지는 맞음
        for (int i = forwards.length - 1; i > -1; i--) {
            System.out.println("idx:" + i);
            if (i > 0) {
                // decent, forwards[i - 1].z()까진 맞음
                DoubleMatrix sigma = forwards[i].backward(decent, forwards[i - 1].z());
                // sigma 이상
                forwards[i].gradient(decent, lambda, size);
                decent = sigma;
            } else {
                forwards[i].gradient(decent.tail(), lambda, size);
            }
        }
        return cost;
    }

    @Override
    public double cost(Network network) {
        DoubleMatrix hypothesis = input;
        double p = 0d;
        FeedForward[] forwards = network.getForwards();
        for (FeedForward forward : forwards) {
            hypothesis = forward.forward(hypothesis);
            p += forward.penalty();
        }
        return LossFunction.LOGISTIC.loss(output, hypothesis) + lambda * p / (2 * size);
    }

    public static class Cost implements Assessed<Network> {
        private double cost;
        private Network network;

        public Cost(double cost, Network network) {
            this.cost = cost;
            this.network = network;
        }

        @Override
        public double cost() {
            return cost;
        }

        @Override
        public Network theta() {
            return network;
        }

        @Override
        public void cost(double cost) {
            this.cost =  cost;
        }

        @Override
        public void theta(Network network) {
            this.network = network;
        }
    }
}

