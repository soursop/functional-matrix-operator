package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

import static com.github.soursop.matrix.operator.utils.Utils.*;

import org.junit.Test;

import java.io.IOException;

public class GradientDecentTest {

    @Test
    public void testSingle() throws IOException {
        double[] data = read("ml/1x_house.txt");
        DenseDoubleMatrix matrix = new DenseDoubleMatrix(2, data);
        Next input = new DoubleOperator(1d).next(matrix.head());
        DoubleMatrix output = matrix.tail();

        DoubleMatrix theta = new DoubleIterator(0d, 2, 1);
        Hypothesis hypothesis = new Hypothesis(input, output);
        print("Expected cost value (approx) 32.07 : %f", hypothesis.error(theta));

        double alpha = 0.01d;
        DoubleOperator ratio = DoubleOperator.of(alpha / matrix.height());
        DoubleMatrix decent = hypothesis.repeat(1500).theta(theta).decent(ratio);
        print("Expected theta values (approx) :");
        print("-3.6303\t1.1664 : %f\t%f", decent.valueOf(0), decent.valueOf(1));
    }

    @Test
    public void testMulti() throws IOException {
        double[] data = read("ml/2x_house.txt");
        DenseDoubleMatrix matrix = new DenseDoubleMatrix(3, data);
        Next input = new DoubleOperator(1d).next(Normalized.of(matrix.init()));
        DoubleMatrix output = matrix.last();

        DoubleMatrix theta = new DoubleIterator(0d, 3, 1);
        DoubleOperator ratio = DoubleOperator.of(0.01d / matrix.height());
        Hypothesis hypothesis = new Hypothesis(input, output);
        DoubleMatrix decent = hypothesis.repeat(400).theta(theta).decent(ratio);
        print("Expected theta values (approx) :");
        print("334300\t100090\t3673.5 : %f\t%f\t%f", decent.valueOf(0), decent.valueOf(1), decent.valueOf(2));

        print("Expected cost value (approx) 2108900000 : %f", hypothesis.error(decent));
    }

}
