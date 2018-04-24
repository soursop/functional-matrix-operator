package com.github.soursop.matrix.operator;


public class NextDoubleMatrix extends DenseDoubleMatrix {

    public static NextDoubleMatrix of(DoubleMatrix... matrices) {
        WithValues combine = combine(matrices);
        return new NextDoubleMatrix(combine.height, combine.width, combine.values);
    }

    protected static WithValues combine(DoubleMatrix... matrices) {
        int width = 0;
        int height = 0;
        for(int i = 0; i < matrices.length; i++) {
            DoubleMatrix matrix = matrices[i];
            width = width + matrix.width();
            height = Assert.assertSameHeightExceptZero(height, matrix.height());
        }
        double[] values = new double[height * width];
        int to = 0;
        for(DoubleMatrix matrix : matrices) {
            for(int h = 0; h < height; h++) {
                double[] row = matrix.row(h);
                System.arraycopy(row, 0, values, indexOf(h, to, width), row.length);
            }
            to = to + matrix.width();
        }
        return new WithValues(width, values);
    }

    protected NextDoubleMatrix(int height, int width, double[] values) {
        super(height, width, values);
    }

}
