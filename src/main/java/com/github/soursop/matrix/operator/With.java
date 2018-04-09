package com.github.soursop.matrix.operator;

public interface With extends Applier {
    DoubleMatrix apply(DoubleMatrix one, DoubleMatrix other);
    DoubleMatrix apply(DoubleMatrix one, DoubleOperator other);
    DoubleMatrix apply(DoubleOperator one, DoubleMatrix other);
    Operator apply(DoubleOperator one, DoubleOperator other);
}
