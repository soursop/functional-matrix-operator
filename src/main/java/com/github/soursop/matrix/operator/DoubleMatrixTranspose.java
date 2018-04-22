package com.github.soursop.matrix.operator;

class DoubleMatrixTranspose extends DenseDoubleMatrix implements Sign.Transpose {

    public static DoubleMatrixTranspose of(DoubleMatrix origin) {
        double[] values = new double[origin.size()];
        int width = origin.width();
        int height = origin.height();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                values[w * height + h] = origin.valueOf(h, w);
            }
        }
        return new DoubleMatrixTranspose(origin.height(), values);
    }

    private DoubleMatrixTranspose(int width, double... values) {
        super(width, values);
    }

}
