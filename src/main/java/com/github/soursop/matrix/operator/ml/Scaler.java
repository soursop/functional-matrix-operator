package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;


public interface Scaler {
    DoubleMatrix before(DoubleMatrix matrix);
    DoubleMatrix after(DoubleMatrix matrix);
}
