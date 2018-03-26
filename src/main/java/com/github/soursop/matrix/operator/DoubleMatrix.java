package com.github.soursop.matrix.operator;


interface DoubleMatrix extends Matrix {
    DoubleMatrix NONE = new DenseDoubleMatrix(0, 0, new double[0]);
    double[] values();
}
