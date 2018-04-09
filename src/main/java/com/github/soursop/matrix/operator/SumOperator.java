package com.github.soursop.matrix.operator;

public class SumOperator extends DoubleOperator {
    private final DoubleMatrix origin;

    public static SumOperator of(DoubleMatrix origin) {
        double result = 0d;
        for (int i = 0; i < origin.size(); i++) {
            result += origin.valueOf(i);
        }
        return new SumOperator(origin, result);
    }

    private SumOperator(DoubleMatrix origin, double value) {
        super(value);
        this.origin = origin;
    }

    DoubleMatrix getDoubleMatrix() {
        return origin;
    }

    public AvgOperator avg() {
        return AvgOperator.of(this);
    }

}
