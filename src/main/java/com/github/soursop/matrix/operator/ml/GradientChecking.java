package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

public class GradientChecking {
    private final MutableDoubleMatrix perturb;
    private final double e = 1e-4;

    public GradientChecking(DoubleMatrix theta) {
        this.perturb = new MutableDoubleMatrix(theta.width(), new double[theta.size()]);
    }

    public DoubleMatrix gradient(DoubleMatrix theta, Gradient gradient) {
        int size = theta.size();
        double[] numgrad = new double[size];
        for (int i = 0; i < size; i++) {
            perturb.set(i, e);
            DoubleMatrix minus = theta.minus(perturb).invoke();
            DoubleMatrix plus = theta.plus(perturb).invoke();
            numgrad[i] = gradient.gradient(plus).cost() - gradient.gradient(minus).cost() / (2 * e);
            perturb.set(i, 0);
        }
        return DenseDoubleMatrix.of(numgrad);
    }
}
