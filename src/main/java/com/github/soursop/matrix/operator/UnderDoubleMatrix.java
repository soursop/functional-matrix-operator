package com.github.soursop.matrix.operator;


public class UnderDoubleMatrix extends DenseDoubleMatrix {

    public static UnderDoubleMatrix of(DoubleMatrix... matrices) {
        WithValues combine = combine(matrices);
        return new UnderDoubleMatrix(combine.height, combine.width, combine.values);
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
            for (int w = 0; w < width; w++) {
                double[] columns = matrix.columns(w);
                System.arraycopy(columns, 0, values, indexOf(from, w, height), columns.length);
            }
            from = from + matrix.height();
        }
        return new WithValues(width, values);
    }

    protected UnderDoubleMatrix(int height, int width, double[] values) {
        super(height, width, values);
    }
}
