package com.github.soursop.matrix.operator.ml;


import com.github.soursop.matrix.operator.DoubleMatrix;

public interface Gradient {
    Cost gradient(DoubleMatrix theta);
    DoubleMatrix predict(DoubleMatrix theta);
    double cost(DoubleMatrix theta);
}
