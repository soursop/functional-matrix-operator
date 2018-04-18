package com.github.soursop.matrix.operator;

/**
 * @author soursop
 * @created 2018. 4. 17.
 */
public class UnderSum extends AbstractDoubleMatrix implements Sign.UnderSum {
    private final double[] values;

    public static UnderSum of(DoubleMatrix origin) {
        double[] doubles = new double[origin.width()];
        for (int w = 0; w < origin.width(); w++) {
            double sum = 0d;
            for (int h = 0; h < origin.height(); h++) {
                sum = sum + origin.valueOf(h, w);
            }
            doubles[w] = sum;
        }
        return new UnderSum(doubles);
    }

    private UnderSum(double[] values) {
        this.values = values;
    }

    @Override
    public int height() {
        return 1;
    }

    @Override
    public int width() {
        return values.length;
    }

    @Override
    public double valueOf(int height, int width) {
        return values[height * width() + width];
    }

}
