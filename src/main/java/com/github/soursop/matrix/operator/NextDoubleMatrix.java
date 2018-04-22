package com.github.soursop.matrix.operator;


public class NextDoubleMatrix extends DenseDoubleMatrix {

    public static NextDoubleMatrix of(DoubleMatrix... matrices) {
        WithValues combine = combine(matrices);
        return new NextDoubleMatrix(combine.width, combine.values);
    }

    protected static WithValues combine(DoubleMatrix... matrices) {
        int width = 0;
        int height = 0;
        int[] range = new int[matrices.length];
        for(int i = 0; i < matrices.length; i++) {
            DoubleMatrix matrix = matrices[i];
            if (i + 1 < matrices.length) {
                range[i + 1] = range[i] + matrix.width();
            }
            width = width + matrix.width();
            height = Assert.assertSameHeightExceptZero(height, matrix.height());
        }
        double[] values = new double[height * width];
        for(int w = 0; w < matrices.length; w++) {
            DoubleMatrix matrix = matrices[w];
            for(int h = 0; h < height; h++) {
                double[] value = matrix.row(h);
                System.arraycopy(value, 0, values, range[w] + h * width, value.length);
            }
        }
        return new WithValues(width, values);
    }

    protected NextDoubleMatrix(int width, double[] values) {
        super(width, values);
    }

}
