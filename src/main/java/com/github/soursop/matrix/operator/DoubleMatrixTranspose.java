package com.github.soursop.matrix.operator;

class DoubleMatrixTranspose extends DenseDoubleMatrix implements Sign.Transpose {

    public static DoubleMatrixTranspose of(DoubleMatrix origin) {
        double[] values = new double[origin.size()];
        int width = origin.width();
        int height = origin.height();
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                values[indexOf(w, h, height)] = origin.valueOf(h, w);
            }
        }
        return new DoubleMatrixTranspose(width, height, values);
    }

    private DoubleMatrixTranspose(int height, int width, double... values) {
        super(height, width, values);
    }

}
