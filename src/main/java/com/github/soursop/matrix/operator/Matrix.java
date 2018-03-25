package com.github.soursop.matrix.operator;

public interface Matrix extends Operator {
    int height();
    int width();
    double valueOf(int height, int width);
    double valueOf(int idx);
    Matrix transpose();
}
