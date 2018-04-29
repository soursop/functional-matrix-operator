package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DenseDoubleMatrix;
import com.github.soursop.matrix.operator.DoubleMatrix;
import org.junit.Test;

import static com.github.soursop.matrix.operator.utils.Utils.print;


public class LogisticRegressionTest {

    @Test
    public void testLogisticRegression() {
        DoubleMatrix x = DenseDoubleMatrix.of(3, new double[]{0.10000, 0.60000, 1.10000, 0.20000, 0.70000, 1.20000, 0.30000, 0.80000, 1.30000, 0.40000, 0.90000, 1.40000, 0.50000, 1.00000, 1.50000});
        DoubleMatrix theta = DenseDoubleMatrix.of(4, new double[]{-2, -1, 1, 2});
        DoubleMatrix y = DenseDoubleMatrix.of(1, new double[]{1d, 0d, 1d, 0d, 1d});

        Regression regression = new Regression(x, y, Activation.SIGMOID, 3);
        Cost cost = regression.gradient(theta);
        print("Cost: %f", cost.cost());
        print("Expected cost: 2.534819");
        print("Gradients:");
        print("%s", cost.theta());
        print("Expected gradients:");
        print("0.146561,-0.548558,0.724722,1.398003");
    }

}
