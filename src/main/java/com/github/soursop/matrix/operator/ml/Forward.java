package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;

public interface Forward {
    DoubleMatrix gradient(DoubleMatrix sigma, double lambda, int size);
    DoubleMatrix forward(DoubleMatrix input);
    DoubleMatrix backward(DoubleMatrix sigma, DoubleMatrix z);
    DoubleMatrix theta();
    double penalty();
    DoubleMatrix z();
}