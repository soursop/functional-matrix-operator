package com.github.soursop.matrix.operator;

/**
 * @author soursop
 * @created 2018. 4. 17.
 */
public class NextSum extends AbstractDoubleMatrix implements Sign.UnderSum {
    private final double[] values;

    public NextSum of(DoubleMatrix origin) {
        double[] doubles = new double[origin.height()];
        for (int h = 0; h < origin.height(); h++) {
            double sum = 0d;
            for (int w = 0; w < origin.width(); w++) {
                sum = sum + origin.valueOf(h, w);
            }
            doubles[h] = sum;
        }
        return new NextSum(doubles);
    }

    private NextSum(double[] values) {
        this.values = values;
    }

    @Override
    public int height() {
        return values.length;
    }

    @Override
    public int width() {
        return 1;
    }

    @Override
    public double valueOf(int height, int width) {
        return values[height * width() + width];
    }

    @Override
    public DoubleMatrix transpose() {
        return new DoubleMatrixTranspose<>(this);
    }
}
