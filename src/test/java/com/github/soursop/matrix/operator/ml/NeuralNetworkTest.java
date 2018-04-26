package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.github.soursop.matrix.operator.utils.Utils.*;
/**
 * @author soursop
 * @created 2018. 4. 19.
 */
public class NeuralNetworkTest {
    private int size = 400;
    DenseDoubleMatrix input;
    DenseDoubleMatrix output;
    DenseDoubleMatrix theta1;
    DenseDoubleMatrix theta2;

    @Before
    public void before() throws IOException {
        input = DenseDoubleMatrix.of(size, gzip("ml/400_mnist.csv.gz"));
        output = OneHotEncoding.of(10, read("ml/400_mnist_label.csv"));

        theta1 = DenseDoubleMatrix.of(size + 1, read("ml/400_mnist_theta01.csv"));
        theta2 = DenseDoubleMatrix.of(theta1.height() + 1, read("ml/400_mnist_theta02.csv"));
    }

    @Test
    public void testZeroCost() {
        FoldDoubleMatrix thetas = FoldDoubleMatrix.of(theta1, theta2);
        NeuralNetwork gradient = new NeuralNetwork(input, output, Activation.SIGMOID, thetas.pos(), 0.00d);
        System.out.println(String.format("*** Cost at parameters : %f", gradient.cost(thetas)));
        System.out.println(String.format("(this value should be about 0.287629)"));
    }

    @Test
    public void testOneCost() {
        FoldDoubleMatrix thetas = FoldDoubleMatrix.of(theta1, theta2);
        NeuralNetwork gradient = new NeuralNetwork(input, output, Activation.SIGMOID, thetas.pos(), 1d);
        Cost cost = gradient.gradient(thetas);
        System.out.println(String.format("*** Cost at parameters : %f", cost.cost()));
        System.out.println(String.format("(this value should be about 0.383770)"));
    }

    @Test
    public void testSigmoidGradient() {
        System.out.println(String.format("*** Evaluating sigmoid gradient..."));
        DoubleMatrix sigmoidGradient = DenseDoubleMatrix.of(5, new double[]{-1d, -0.5d, 0d, 0.5d, 1d}).apply(Activation.SIGMOID.gradient());
        System.out.println(String.format("Sigmoid gradient evaluated at [-1 -0.5 0 0.5 1]:%s", sigmoidGradient));
    }

    @Test
    public void testAddLambdaCost() {
        FoldDoubleMatrix thetas = FoldDoubleMatrix.of(theta1, theta2);
        NeuralNetwork gradient = new NeuralNetwork(input, output, Activation.SIGMOID, thetas.pos(), 3d);
        Cost cost = gradient.gradient(thetas);
        System.out.println(String.format("Estimated J(theta) = 0.576051 : %f", cost.cost()));
    }

    @Test
    public void testRandom() {
        DoubleMatrix theta1 = new DoubleEpsilonIterator(this.theta1.height(), this.theta1.width(), 0.12d);
        DoubleMatrix theta2 = new DoubleEpsilonIterator(this.theta2.height(), this.theta2.width(), 0.12d);
        FoldDoubleMatrix thetas = FoldDoubleMatrix.of(theta1, theta2);
        NeuralNetwork gradient = new NeuralNetwork(input, output, Activation.SIGMOID, thetas.pos(), 1d);
        DoubleMatrix minimize = Fmincg.minimize(gradient, thetas, 50, true);

    }
}
