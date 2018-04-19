package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;
import com.github.soursop.matrix.operator.NextDoubleMatrix;
import com.github.soursop.matrix.operator.StdOperator;

public class Normalized extends NextDoubleMatrix {

    public static Normalized of(DoubleMatrix one) {
        DoubleMatrix[] transformed = new DoubleMatrix[one.width()];
        for (int i = 0; one.isSome(); i++) {
            DoubleMatrix head = one.head();
            StdOperator std = head.std();
            transformed[i] = head.minus(std.avg()).divide(std).invoke();
            one = one.tail();
        }
        return new Normalized(transformed);
    }

    private Normalized(DoubleMatrix... matrices) {
        super(matrices);
    }

}
