package com.github.soursop.matrix.operator;

public class SumOperator extends DoubleOperator implements Sign.Sum {
    private final DoubleMatrix origin;
    private final MaxOperator max;
    private final MinOperator min;

    public static SumOperator of(Operator origin) {
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        double result = 0d;
        DoubleMatrix matrix = origin.asDoubleMatrix();
        for (int i = 0; i < matrix.size(); i++) {
            double v = matrix.valueOf(i);
            result += v;
            max = Math.max(max, v);
            min = Math.min(min, v);
        }
        return new SumOperator(matrix, new MaxOperator(max), new MinOperator(min), result);
    }

    private SumOperator(DoubleMatrix origin, MaxOperator max, MinOperator min, double value) {
        super(value);
        this.origin = origin;
        this.max = max;
        this.min = min;
    }

    DoubleMatrix getDoubleMatrix() {
        return origin;
    }

    public AvgOperator avg() {
        return AvgOperator.of(this);
    }

    public MaxOperator max() {
        return max;
    }

    public MinOperator min() {
        return min;
    }

}
