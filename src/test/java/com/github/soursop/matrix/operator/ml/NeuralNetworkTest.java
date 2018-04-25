package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;
import org.junit.Before;
import org.junit.BeforeClass;
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
    public void testZeroCost() throws IOException {
        Network network = Network.of(Activation.SIGMOID, theta1, theta2);
        NeuralNetwork gradient = new NeuralNetwork(input, output, 0.00d);
        System.out.println(String.format("\n*** Cost at parameters : %f", gradient.cost(network)));
        System.out.println(String.format("(this value should be about 0.287629)"));
    }

    @Test
    public void testOneCost() throws IOException {
        Network network = Network.of(Activation.SIGMOID, theta1, theta2);
        NeuralNetwork gradient = new NeuralNetwork(input, output, 1d);
        Assessed<Network> cost = gradient.gradient(gradient.init(network));
        System.out.println(String.format("\n*** Cost at parameters : %f", gradient.cost(cost.theta())));
        System.out.println(String.format("(this value should be about 0.383770)"));
    }
}
