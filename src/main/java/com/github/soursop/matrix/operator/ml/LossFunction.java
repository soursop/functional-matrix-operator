package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

import static com.github.soursop.matrix.operator.DoubleOperator.ONE;

public interface LossFunction {
    double loss(DoubleMatrix y, DoubleMatrix hypothesis);

    LossFunction LOG_LOSS = new LossFunction() {
        @Override
        public double loss(DoubleMatrix y, DoubleMatrix hypothesis) {
            Multiply positive = y.minus().multiply(hypothesis.apply(Function.LOG));
            Multiply negative = ONE.minus(y).multiply(ONE.minus(hypothesis).apply(Function.LOG));
            return positive.minus(negative).sum().getValue() / y.height();
        }
    }
    ;
}
