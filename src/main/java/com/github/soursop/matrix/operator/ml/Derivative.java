package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;
import com.github.soursop.matrix.operator.Operator;

public interface Derivative extends Measurable {
    Operator invoke(DoubleMatrix theta);
}
