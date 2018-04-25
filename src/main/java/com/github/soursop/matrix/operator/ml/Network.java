package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;

/**
 * @author soursop
 * @created 2018. 4. 25.
 */
public class Network {
    private final FeedForward[] forwards;

    private Network(FeedForward[] forwards) {
        this.forwards = forwards;
    }

    public static Network of(Activation activation, DoubleMatrix ... thetas) {
        FeedForward[] forwards = new FeedForward[thetas.length];
        for (int i = 0; i < thetas.length; i++) {
            forwards[i] = new FeedForward(thetas[i], activation);
        }
        return new Network(forwards);
    }

    public FeedForward[] getForwards() {
        return forwards;
    }

    public DoubleMatrix[] theta() {
        DoubleMatrix[] thetas = new DoubleMatrix[forwards.length];
        for (int i = 0; i < forwards.length; i++) {
            thetas[i] = forwards[i].theta();
        }
        return thetas;
    }
}
