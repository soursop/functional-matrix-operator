package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;

import static com.github.soursop.matrix.operator.utils.Utils.*;

import org.junit.Test;

import java.io.IOException;

public class GradientDecentTest {

    @Test
    public void testSingle() throws IOException {
        double[] data = read("ml/ex01/data1.txt");
        DenseDoubleMatrix matrix = new DenseDoubleMatrix(2, data);
        DoubleOperator size = new DoubleOperator(matrix.height());
        Next input = new DoubleOperator(1d).next(matrix.head());
        DoubleMatrix output = matrix.tail();

        int DERIVATIVE_OF_POW = 2;
        DoubleMatrix theta = new DoubleIterator(0d, 2, 1);
        Multiply hypothesis = input.multiply(theta);
        Plus cost = hypothesis.minus(output);
        double squaredError = cost.pow(DERIVATIVE_OF_POW).invoke().avg() / DERIVATIVE_OF_POW;
        print("Expected cost value (approx) 32.07 : %f", squaredError);

        DoubleOperator alpha = DoubleOperator.of(0.01d);
        int repeat = 1500;
        for (int i = 0; i < repeat; i++) {
            theta = gradientDecent(input, output, theta, size, alpha);
        }
        print("Expected theta values (approx) :");
        print("-3.6303\t1.1664 : %f\t%f", theta.valueOf(0), theta.valueOf(1));
    }

    private DoubleMatrix gradientDecent(Next input, DoubleMatrix output, DoubleMatrix theta, DoubleOperator size, DoubleOperator alpha) {
        Multiply hypothesis = input.multiply(theta);
        Plus cost = hypothesis.minus(output);
        Multiply gradient = cost.multiply(alpha).divide(size);
        Operators decent = input.transpose().multiply(gradient);
        return theta.minus(decent).invoke();
    }

    @Test
    public void testMulti() throws IOException {
        double[] data = read("ml/ex01/data2.txt");
        DenseDoubleMatrix matrix = new DenseDoubleMatrix(3, data);
        DoubleMatrix input = matrix.init();
        DoubleMatrix output = matrix.last();
    }
}
