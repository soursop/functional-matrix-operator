package com.github.soursop.matrix.operator.ml;


import com.github.soursop.matrix.operator.DoubleMatrix;

/**
 * @author soursop
 * @created 2018. 4. 25.
 */
public class Cost {
    private final double cost;
    private final DoubleMatrix theta;

    public Cost(double cost, DoubleMatrix theta) {
        this.cost = cost;
        this.theta = theta;
    }

    public double cost() {
        return cost;
    }

    public DoubleMatrix theta() {
        return theta;
    }
}
