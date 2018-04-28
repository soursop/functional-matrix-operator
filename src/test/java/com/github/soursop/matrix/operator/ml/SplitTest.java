package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

public class SplitTest {

    @Ignore
    @Test
    public void testForkAndJoin() throws InterruptedException {
        int height = 1_00_000;
        int width = 20;
        int size = 30;
        double[] values1 = new DoubleRandomIterator(height, width, 0).values();
        double[] values2 = new DoubleRandomIterator(width, size, 0).values();
        final DenseDoubleMatrix one = DenseDoubleMatrix.of(width, values1);
        final Product product = new Product(DenseDoubleMatrix.of(size, values2));

        int parallelism = 4;
        int w = 1;
//        int w = 3;
        int from = 4;
        int to = 8;
        int until = to - from;
        int repeat = 5;
        double[] data = new double[w * until * repeat];
        double[] out = new double[until * repeat];

        ForkJoinPool pool = new ForkJoinPool(parallelism);

        for (int i = 0; i < until; i++) {
            int split = height / (i + from);
            for (int r = 0; r < repeat; r++) {
                long s2 = System.currentTimeMillis();
                pool.invoke(new SplitDoubleMatrix(one, product, split));
                double elaps = System.currentTimeMillis() - s2;
                int idx = (i * repeat + r) * w;
                data[idx] = split;
//                data[idx + 1] = height;
//                data[idx + 2] = parallelism;
                out[idx / w] = elaps;
//                System.out.println("thread result size: " + resultBySplit.size());
//                System.out.println("thread elaps time: " + elaps);
            }
        }
        DenseDoubleMatrix input = DenseDoubleMatrix.of(w, data);
        DenseDoubleMatrix output = DenseDoubleMatrix.of(out);
        System.out.println(input);
        System.out.println();
        System.out.println(output);
        DenseDoubleMatrix normalized = Normalized.of(input);


        DoubleMatrix theta = new DoubleEpsilonIterator(1, normalized.width() + 1, 0.2d);

        LinearRegression function = new LinearRegression(normalized, output, 0.9d);
        Cost cost = new Until(theta).repeat(30).by(function);
//        DoubleMatrix matrix = Fmincg.asMinimize(function, theta, 50);
        StdOperator std = input.head().std();
        double in = ((8 / height - std.avg().getValue()) / std.getValue());
        DenseDoubleMatrix test = DenseDoubleMatrix.of(w + 1, new double[]{1, in, height, parallelism});
        Normalized of = Normalized.of(test);
        DoubleMatrix result = of.product(cost.theta().transpose()).invoke();
        System.out.println(result);

        System.out.println(cost.theta());
//        System.out.println(matrix);
    }
}
