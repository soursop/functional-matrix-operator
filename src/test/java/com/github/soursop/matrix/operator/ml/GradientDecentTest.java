package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

import static com.github.soursop.matrix.operator.utils.Utils.*;

import org.junit.Test;

import java.io.IOException;

public class GradientDecentTest {

    @Test
    public void testSingle() throws IOException {
        double[] data = read("ml/1x_house.csv");
        DenseDoubleMatrix matrix = DenseDoubleMatrix.of(2, data);
        Layer input = Layer.of(matrix.head());
        DoubleMatrix output = matrix.tail();

        GradientDecent gradient = new GradientDecent(input, output, 0.01d);
        DoubleMatrix theta = new DoubleIterator(0d, 2, 1);
        print("Expected cost value (approx) 32.07 : %f", gradient.cost(theta));

        DoubleMatrix decent = new Until(theta).repeat(1500).by(gradient).theta();
        print("Expected theta values (approx) :");
        print("-3.6303\t1.1664 : %f\t%f", decent.valueOf(0), decent.valueOf(1));
    }

    @Test
    public void testMulti() throws IOException {
        double[] data = read("ml/2x_house.csv");
        DenseDoubleMatrix matrix = DenseDoubleMatrix.of(3, data);
        Layer input = Layer.of(Normalized.of(matrix.init()));
        DoubleMatrix output = matrix.last();

        DoubleMatrix theta = new DoubleIterator(0d, 3, 1);
        GradientDecent gradient = new GradientDecent(input, output, 0.01d);
        DoubleMatrix decent = new Until(theta).repeat(400).by(gradient).theta();
        print("Expected theta values (approx) :");
        print("334300\t100090\t3673.5 : %f\t%f\t%f", decent.valueOf(0), decent.valueOf(1), decent.valueOf(2));
        print("Expected cost value (approx) 2108900000 : %f", gradient.cost(decent));
    }

    @Test
    public void testRegularization() throws IOException {
        double[] data = read("ml/2x_house.csv");
        DenseDoubleMatrix matrix = DenseDoubleMatrix.of(3, data);
        DoubleMatrix input = Normalized.of(matrix.init());
        DoubleMatrix output = matrix.last();
        DoubleMatrix theta = new DoubleIterator(0, 1, 3);
        Regression regression = new Regression(input, output, Activation.DEFAULT, 0.01d);
//        DoubleMatrix matrix1 = Fmincg.asMinimize(regression, theta, 40);
        Cost cost = new Until(theta).repeat(20).by(regression);
        System.out.println(cost.theta());

    }

}
