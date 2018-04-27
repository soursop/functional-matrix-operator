package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;

public interface IterationListener {
    void onFinished(int iteration, double cost, DoubleMatrix currentWeights);
}
