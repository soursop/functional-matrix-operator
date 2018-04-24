package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;
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
        DenseDoubleMatrix input = DenseDoubleMatrix.of(size, gzip("ml/400_mnist.csv.gz"));
        OneHotEncoding output = OneHotEncoding.of(10, read("ml/400_mnist_label.csv"));

        DenseDoubleMatrix theta1 = DenseDoubleMatrix.of(size + 1, read("ml/400_mnist_theta01.csv"));
        DenseDoubleMatrix theta2 = DenseDoubleMatrix.of(theta1.height() + 1, read("ml/400_mnist_theta02.csv"));
        FeedForward[] forwards = asArray(new FeedForward(theta1, Activation.SIGMOID), new FeedForward(theta2, Activation.SIGMOID));

        long s1 = System.currentTimeMillis();
        double p = 0d;
        double lambda = 0.00d;
        DoubleMatrix hypothesis = input;
        for (FeedForward forward : forwards) {
            hypothesis = forward.forward(hypothesis);
            p += forward.penalty();
        }

        double loss = LossFunction.LOGISTIC.loss(output, hypothesis) + lambda * p / (2 * input.height());
        System.out.println(loss);

        System.out.println(System.currentTimeMillis() - s1);

//        for (int j = 0; j < 200; j++) {
            long s2 = System.currentTimeMillis();
            DoubleMatrix decent = hypothesis.minus(output).invoke();
            for (int i = forwards.length - 1; i > -1; i--) {
                if (i > 0) {
                    DoubleMatrix sigma = forwards[i].backward(decent, forwards[i - 1].z());
                    decent = sigma;
                } else {
                    forwards[i].gradient(decent.tail(), lambda, input.height());
                }
            }
            System.out.println(System.currentTimeMillis() - s2);
//        }

    }
}
