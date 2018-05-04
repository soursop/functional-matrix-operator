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
        double[] x = read("ml/1x_liner.csv");
        double[] y = read("ml/1y_liner.csv");
        DenseDoubleMatrix input = DenseDoubleMatrix.of(1, x);
        DenseDoubleMatrix output = DenseDoubleMatrix.of(1, y);
        DoubleMatrix theta = new DoubleIterator(1, 1, 2);
        Regression regression = new Regression(input, output, Activation.DEFAULT, 1d);
        Cost gradient = regression.gradient(theta);
        print("[Cost at theta = [1 ; 1]: %f ...\n" +
                "         (this value should be about 303.993192)], J", gradient.cost());
        print("[Gradient at theta = [1 ; 1]:  [%f; %f] ...\n" +
                "         (this value should be about [-15.303016; 598.250744])]", gradient.theta().valueOf(0), gradient.theta().valueOf(1));
    }

    @Test
    public void testFmincgRegularization() throws IOException {
        double[] x = read("ml/1x_liner.csv");
        double[] y = read("ml/1y_liner.csv");
        DenseDoubleMatrix input = DenseDoubleMatrix.of(1, x);
        DenseDoubleMatrix output = DenseDoubleMatrix.of(1, y);
        DoubleMatrix theta = new DoubleIterator(0, 1, 2);
        Regression regression = new Regression(input, output, Activation.DEFAULT, 0);
        DoubleMatrix result = Fmincg.asMinimize(regression, theta, 200);
        System.out.println(result);
    }

}
