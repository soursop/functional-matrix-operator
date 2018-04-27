package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;

public interface Minimizer {
    DoubleMatrix minimize(Gradient f, DoubleMatrix theta, int length);
}
