package com.github.soursop.matrix.operator;

import java.util.List;

public interface DoubleMatrix extends Operator, Transposable<DoubleMatrix> {
    int height();
    int width();
    double valueOf(int height, int width);
    double valueOf(int idx);
    double[] values();
    DoubleMatrix transpose();
    List<DoubleMatrix> splitBy(int size);
    int size();
    DoubleMatrix head();
    DoubleMatrix tail();
    DoubleMatrix last();
    DoubleMatrix init();
    double sum();
    double avg();
}
