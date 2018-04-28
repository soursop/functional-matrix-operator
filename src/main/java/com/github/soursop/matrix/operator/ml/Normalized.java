package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;
import com.github.soursop.matrix.operator.NextDoubleMatrix;
import com.github.soursop.matrix.operator.StdOperator;

public class Normalized extends NextDoubleMatrix {
    private Normalized(int height, int width, double[] combine) {
        super(height, width, combine);
    }

    public static Normalized of(DoubleMatrix one) {
        DoubleMatrix[] transformed = new DoubleMatrix[one.width()];
        for (int i = 0; one.isSome(); i++) {
            DoubleMatrix head = one.head();
            StdOperator std = head.std();
            if (std.getValue() == 0) {
                transformed[i] = head;
            } else {
                transformed[i] = head.minus(std.avg()).divide(std).invoke();
            }
            one = one.tail();
        }
        WithValues combine = combine(transformed);
        return new Normalized(combine.height, combine.width, combine.values);
    }

}
