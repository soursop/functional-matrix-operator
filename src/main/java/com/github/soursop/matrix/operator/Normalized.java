package com.github.soursop.matrix.operator;

public class Normalized extends NextDoubleMatrix {
    public Normalized of(DoubleMatrix one) {
        DoubleMatrix[] transformed = new DoubleMatrix[one.width()];
        for (int i = 0; one.isSome(); i++) {
            DoubleMatrix head = one.head();
            StdOperator std = StdOperator.of(head);
            transformed[i] = head.minus(std.avg()).divide(std).invoke();
            one = one.tail();
        }
        return new Normalized(transformed);
    }

    private Normalized(DoubleMatrix... matrices) {
        super(matrices);
    }
}
