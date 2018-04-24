package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;

public interface Assessed {
    double cost(DoubleMatrix theta);
}
