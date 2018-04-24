package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;

public interface Derivative extends Assessed {
    DoubleMatrix gradient(DoubleMatrix ... thetas);
}
