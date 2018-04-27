package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;
import com.github.soursop.matrix.operator.FoldDoubleMatrix;

/**
 * @author soursop
 * @created 2018. 4. 24.
 */
public class NeuralNetwork implements Derivative {
    private final DoubleMatrix input;
    private final DoubleMatrix output;
    private final double lambda;
    private final int size;
    private final int[] pos;
    private final FeedForward[] forwards;

    public NeuralNetwork(DoubleMatrix input, DoubleMatrix output, Activation activation, int[] pos, double lambda) {
        this.input = input;
        this.output = output;
        this.lambda = lambda;
        size = input.height();
        forwards = new FeedForward[pos.length / 2];
        for (int i = 0; i < forwards.length; i++) {
            forwards[i] = new FeedForward(activation);
        }
        this.pos = pos;
    }

    @Override
    public Cost gradient(DoubleMatrix thetas) {
        DoubleMatrix[] unfold = FoldDoubleMatrix.unfold(thetas, pos);
        DoubleMatrix hypothesis = input;
        double p = 0d;
        for (int i = 0; i < forwards.length; i++) {
            FeedForward forward = forwards[i];
            DoubleMatrix theta = unfold[i];
            hypothesis = forward.forward(theta, hypothesis);
            p += forward.penalty(theta);
        }

        double loss = LossFunction.LOGISTIC.loss(output, hypothesis) + lambda * p / (2 * size);

        DoubleMatrix decent = hypothesis.minus(output).invoke();
        DoubleMatrix[] gradient = new DoubleMatrix[unfold.length];
        for (int i = forwards.length - 1; i > -1; i--) {
            if (i > 0) {
                DoubleMatrix sigma = forwards[i].backward(unfold[i], decent, forwards[i - 1].z());
                gradient[i] = forwards[i].gradient(unfold[i], decent, lambda, size);
                decent = sigma;
            } else {
                gradient[i] = forwards[i].gradient(unfold[i], decent.tail(), lambda, size);
            }
        }
        return new Cost(loss, FoldDoubleMatrix.of(gradient));
    }

    @Override
    public double cost(DoubleMatrix thetas) {
        DoubleMatrix[] unfold = FoldDoubleMatrix.unfold(thetas, pos);
        DoubleMatrix hypothesis = input;
        double p = 0d;
        for (int i = 0; i < forwards.length; i++) {
            FeedForward forward = forwards[i];
            DoubleMatrix theta = unfold[i];
            hypothesis = forward.forward(theta, hypothesis);
            p += forward.penalty(theta);
        }
        return LossFunction.LOGISTIC.loss(output, hypothesis) + lambda * p / (2 * size);
    }

    @Override
    public DoubleMatrix predict(DoubleMatrix thetas) {
        DoubleMatrix[] unfold = FoldDoubleMatrix.unfold(thetas, pos);
        DoubleMatrix hypothesis = input;
        for (int i = 0; i < forwards.length; i++) {
            FeedForward forward = forwards[i];
            DoubleMatrix theta = unfold[i];
            hypothesis = forward.forward(theta, hypothesis);
        }
        return hypothesis;
    }

}

