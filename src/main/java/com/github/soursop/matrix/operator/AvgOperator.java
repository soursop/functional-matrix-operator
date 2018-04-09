package com.github.soursop.matrix.operator;

public class AvgOperator extends DoubleOperator {
    private final SumOperator sum;

    public static AvgOperator of(DoubleMatrix origin) {
        return SumOperator.of(origin).avg();
    }

    public static AvgOperator of(SumOperator sum) {
        double result = sum.getValue() / sum.getDoubleMatrix().size();
        return new AvgOperator(sum, result);
    }

    private AvgOperator(SumOperator sum, double value) {
        super(value);
        this.sum = sum;
    }

    DoubleMatrix getDoubleMatrix() {
        return sum.getDoubleMatrix();
    }

    public StdOperator std() {
        return StdOperator.of(this);
    }

    public SumOperator sum() {
        return sum;
    }
}
