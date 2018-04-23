package com.github.soursop.matrix.operator;


public class NextDoubleMatrix extends DenseDoubleMatrix {

    public static NextDoubleMatrix of(DoubleMatrix... matrices) {
        WithValues combine = combine(matrices);
        return new NextDoubleMatrix(combine.width, combine.values);
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
        for(int m = 0; m < matrices.length; m++) {
            DoubleMatrix matrix = matrices[m];
            for(int h = 0; h < height; h++) {
                double[] value = matrix.row(h);
                System.arraycopy(value, 0, values, to + h * width, value.length);
            }
            to = to + matrix.width();
        }
        return new WithValues(width, values);
    }

    protected NextDoubleMatrix(int width, double[] values) {
        super(width, values);
    }

}
