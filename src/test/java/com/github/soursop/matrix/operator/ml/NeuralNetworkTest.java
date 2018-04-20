package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DenseDoubleMatrix;
import org.junit.Test;

import java.io.IOException;

import static com.github.soursop.matrix.operator.Functions.asArray;
import static com.github.soursop.matrix.operator.utils.Utils.*;
/**
 * @author soursop
 * @created 2018. 4. 19.
 */
public class NeuralNetworkTest {

    @Test
    public void test() throws IOException {
        int size = 400;
        DenseDoubleMatrix input = new DenseDoubleMatrix(size, gzip("ml/400_mnist.csv.gz"));
        OneHotEncoding output = OneHotEncoding.of(10, read("ml/400_mnist_label.csv"));

        DenseDoubleMatrix theta1 = new DenseDoubleMatrix(size, read("ml/400_mnist_theta01.csv"));
        DenseDoubleMatrix theta2 = new DenseDoubleMatrix(theta1.height(), read("ml/400_mnist_theta02.csv"));
        DenseDoubleMatrix[] thetas = asArray(theta1, theta2);


    }
}
