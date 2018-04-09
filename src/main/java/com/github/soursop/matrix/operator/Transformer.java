package com.github.soursop.matrix.operator;

public interface Transformer extends Applier {
    DoubleMatrix apply(DoubleMatrix one);

    Transformer NORMALIZER = new Transformer() {
        @Override
        public DoubleMatrix apply(DoubleMatrix one) {
            DoubleMatrix[] transformed = new DoubleMatrix[one.width()];
            for (int i = 0; one.isSome(); i++) {
                DoubleMatrix head = one.head();
                StdOperator std = StdOperator.of(head);
                transformed[i] = head.minus(std.avg()).divide(std).invoke();
                one = one.tail();
            }
            return new NextDoubleMatrix(transformed);
        }

        @Override
        public String symbol() {
            return "norm";
        }
    }
    ;
}
