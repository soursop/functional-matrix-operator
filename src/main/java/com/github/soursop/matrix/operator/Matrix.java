package com.github.soursop.matrix.operator;

public interface Matrix {
    double[] values();
    int height();
    int width();
    double valueOf(int height, int width);
    double valueOf(int idx);
    Matrix multiply(Matrix vector);
    Matrix transpose();
}
