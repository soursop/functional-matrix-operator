package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;

public interface Measurable {
    double cost(DoubleMatrix theta);
}
