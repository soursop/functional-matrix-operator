package com.github.soursop.matrix.operator;


public class UnderDoubleMatrix extends DenseDoubleMatrix {

    public static UnderDoubleMatrix of(DoubleMatrix... matrices) {
        WithValues combine = combine(matrices);
        return new UnderDoubleMatrix(combine.width, combine.values);
    }

    protected static WithValues combine(DoubleMatrix... matrices) {
        int width = 0;
        int height = 0;
        for(DoubleMatrix matrix : matrices) {
            height = height + matrix.height();
            width = Assert.assertSameWidthExceptZero(width, matrix.width());
        }
        double[] values = new double[height * width];
        int from = 0;
        for(DoubleMatrix matrix : matrices) {
            double[] value = matrix.values();
            System.arraycopy(value, 0, values, from, value.length);
            from = from + value.length;
        }
        return new WithValues(width, values);
    }

    protected UnderDoubleMatrix(int width, double[] values) {
        super(width, values);
    }
}
