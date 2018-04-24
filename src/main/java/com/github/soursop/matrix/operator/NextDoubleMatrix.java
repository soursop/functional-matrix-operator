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
            double[] value = matrix.values();
            int _width = matrix.width();
            for(int h = 0; h < height; h++) {
                System.arraycopy(value, indexOf(h, _width), values, indexOf(h, to, width), _width);
            }
            to = to + _width;
        }
        return new WithValues(width, values);
    }

    protected NextDoubleMatrix(int height, int width, double[] values) {
        super(height, width, values);
    }

}
