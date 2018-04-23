package com.github.soursop.matrix.operator;

import java.util.List;

public interface DoubleMatrix extends Matrix, Operator, Transposable<DoubleMatrix> {
    double valueOf(int height, int width);
    double[] columns(int width);
    double valueOf(int idx);
    double[] values();
    DoubleMatrix transpose();
    List<DoubleMatrix> splitBy(int size);
    DoubleMatrix head();
    DoubleMatrix tail();
    DoubleMatrix last();
    DoubleMatrix init();
}
