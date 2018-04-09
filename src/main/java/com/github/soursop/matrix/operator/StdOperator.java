package com.github.soursop.matrix.operator;

public class StdOperator extends DoubleOperator {
    private final AvgOperator avg;

    public static StdOperator of(DoubleMatrix origin) {
        return AvgOperator.of(origin).std();
    }

    public static StdOperator of(AvgOperator avg) {
        double result = 0d;
        DoubleMatrix doubleMatrix = avg.getDoubleMatrix();
        for (int i = 0; i < doubleMatrix.size(); i++) {
            result += Math.pow(doubleMatrix.valueOf(i) - avg.getValue(), 2);
        }
        double std = result / doubleMatrix.size();
        return new StdOperator(avg, std);
    }

    private StdOperator(AvgOperator avg, double value) {
        super(value);
        this.avg = avg;
    }

    public SumOperator sum() {
        return avg.sum();
    }

    public AvgOperator avg() {
        return avg;
    }

}
