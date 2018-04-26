package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;

public interface Forward {
    DoubleMatrix gradient(DoubleMatrix theta, DoubleMatrix sigma, double lambda, int size);
    DoubleMatrix forward(DoubleMatrix theta, DoubleMatrix input);
    DoubleMatrix backward(DoubleMatrix theta, DoubleMatrix sigma, DoubleMatrix z);
    double penalty(DoubleMatrix theta);
    DoubleMatrix z();
}