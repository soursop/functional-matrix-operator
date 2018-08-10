package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DenseDoubleMatrix;
import com.github.soursop.matrix.operator.DoubleMatrix;
import com.github.soursop.matrix.operator.NextDoubleMatrix;
import com.github.soursop.matrix.operator.StdOperator;

public class Normalized extends NextDoubleMatrix implements Scaler {
    private final StdOperator[] stdOperators;
    private Normalized(StdOperator[] stdOperators, int height, int width, double[] combine) {
        super(height, width, combine);
        this.stdOperators = stdOperators;
    }

    public static Normalized of(DoubleMatrix one) {
        DoubleMatrix[] transformed = new DoubleMatrix[one.width()];
        StdOperator[] stdOperators = new StdOperator[one.width()];
        for (int i = 0; one.isSome(); i++) {
            DoubleMatrix head = one.head();
            StdOperator std = head.std();
            stdOperators[i] = std;
            if (std.getValue() == 0) {
                transformed[i] = head;
            } else {
                transformed[i] = head.minus(std.avg()).divide(std).invoke();
            }
            one = one.tail();
        }
        WithValues combine = combine(transformed);
        return new Normalized(stdOperators, combine.height, combine.width, combine.values);
    }

    @Override
    public DoubleMatrix before(DoubleMatrix matrix) {
        DoubleMatrix[] transformed = new DoubleMatrix[matrix.width()];
        for (int i = 0; matrix.isSome(); i++) {
            DoubleMatrix head = matrix.head();
            StdOperator std = stdOperators[i];
            if (std.getValue() == 0) {
                transformed[i] = head;
            } else {
                transformed[i] = head.minus(std.avg()).divide(std).invoke();
            }
            matrix = matrix.tail();
        }
        WithValues combine = combine(transformed);
        return DenseDoubleMatrix.of(combine.width, combine.values);
    }

    @Override
    public DoubleMatrix after(DoubleMatrix matrix) {
        DoubleMatrix[] transformed = new DoubleMatrix[matrix.width()];
        for (int i = 0; matrix.isSome(); i++) {
            DoubleMatrix head = matrix.head();
            StdOperator std = stdOperators[i];
            if (std.getValue() == 0) {
                transformed[i] = head;
            } else {
                transformed[i] = head.multiply(std).plus(std.avg()).invoke();
            }
            matrix = matrix.tail();
        }
        WithValues combine = combine(transformed);
        return DenseDoubleMatrix.of(combine.width, combine.values);
    }
}
