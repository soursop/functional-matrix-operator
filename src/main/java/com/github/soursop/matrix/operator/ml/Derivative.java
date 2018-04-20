package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;

public interface Derivative extends Measurable {
    DoubleMatrix invoke(DoubleMatrix theta);
}
