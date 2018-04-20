package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

import static com.github.soursop.matrix.operator.utils.Utils.*;

import org.junit.Test;

import java.io.IOException;

public class GradientDecentTest {

    @Test
    public void testSingle() throws IOException {
        double[] data = read("ml/1x_house.csv");
        DenseDoubleMatrix matrix = new DenseDoubleMatrix(2, data);
        Layer input = new Layer(matrix.head());
        DoubleMatrix output = matrix.tail();

        Cost cost = new Cost(input, output);
        DoubleIterator theta = new DoubleIterator(0d, 2, 1);
        print("Expected cost value (approx) 32.07 : %f", cost.cost(theta));

        Gradient gradient = new Gradient(input, output, 0.01d);
        DoubleMatrix decent = Until.of(theta).repeat(1500).by(gradient);
        print("Expected theta values (approx) :");
        print("-3.6303\t1.1664 : %f\t%f", decent.valueOf(0), decent.valueOf(1));
    }

    @Test
    public void testMulti() throws IOException {
        double[] data = read("ml/2x_house.csv");
        DenseDoubleMatrix matrix = new DenseDoubleMatrix(3, data);
        Layer input = new Layer(Normalized.of(matrix.init()));
        DoubleMatrix output = matrix.last();

        DoubleMatrix theta = new DoubleIterator(0d, 3, 1);
        Gradient gradient = new Gradient(input, output, 0.01d);
        DoubleMatrix decent = Until.of(theta).repeat(400).by(gradient);
        print("Expected theta values (approx) :");
        print("334300\t100090\t3673.5 : %f\t%f\t%f", decent.valueOf(0), decent.valueOf(1), decent.valueOf(2));
        print("Expected cost value (approx) 2108900000 : %f", gradient.cost(decent));
    }

}
