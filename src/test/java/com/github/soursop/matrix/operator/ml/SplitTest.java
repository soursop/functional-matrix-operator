package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

import static com.github.soursop.matrix.operator.utils.Utils.read;

public class SplitTest {

    @Ignore
    @Test
    public void testForkAndJoin() throws InterruptedException {
        int height = 500_000;
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
        int repeat = 20;
        int warmup = 5;
        double[] data = new double[w * until * repeat];
        double[] out = new double[until * repeat];

        ForkJoinPool pool = new ForkJoinPool(parallelism);

        for (int i = 0; i < until; i++) {
            int split = height / (i + from);
            for (int r = 0; r < repeat; r++) {
                for (int f = 0; f < warmup; f++) {
                    pool.invoke(new SplitDoubleMatrix(one, product, split));
                }
                long s2 = System.currentTimeMillis();
                pool.invoke(new SplitDoubleMatrix(one, product, split));
                double elaps = System.currentTimeMillis() - s2;
                int idx = (i * repeat + r) * w;
                data[idx] = split;
//                data[idx + 1] = height;
//                data[idx + 2] = parallelism;
                out[idx / w] = elaps;
                System.out.println(split + "," + elaps);
            }
        }
        DenseDoubleMatrix input = DenseDoubleMatrix.of(w, data);
        DenseDoubleMatrix output = DenseDoubleMatrix.of(out);
        DenseDoubleMatrix normalized = Normalized.of(input);

//        DoubleMatrix theta = new DoubleEpsilonIterator(normalized.width() + 1, 1, 0.2d);
        DoubleMatrix theta = new DoubleIterator(0,normalized.width() + 1, 1);

        GradientDecent function = new GradientDecent(Layer.of(input), output, 1d);
        Cost cost = new Until(theta).repeat(30).by(function);
//        DoubleMatrix expect = Fmincg.asMinimize(function, theta, 50);
        expect(4, height, w, input, cost.theta());
        expect(8, height, w, input, cost.theta());

    }

    private void expect(int size, int height, int w, DenseDoubleMatrix input, DoubleMatrix expect) {
        StdOperator std = input.head().std();
        double in = ((size / height - std.avg().getValue()) / std.getValue());
        DenseDoubleMatrix test = DenseDoubleMatrix.of(w + 1, new double[]{1, size});
//        DenseDoubleMatrix test = DenseDoubleMatrix.of(w + 1, new double[]{1, in, height, parallelism});
        DoubleMatrix result = test.product(expect).invoke();
        System.out.println(in + " result:" + result);
        System.out.println(expect);
    }

    @Test
    public void testSplit() throws IOException {
        double[] data = read("ml/split");
        DenseDoubleMatrix matrix = DenseDoubleMatrix.of(2, data);
        Layer input = Layer.of(matrix.head());
        DoubleMatrix output = matrix.tail();

        GradientDecent gradient = new GradientDecent(input, output, 1d);
        DoubleMatrix theta = new DoubleIterator(0d, 2, 1);

        DoubleMatrix decent = new Until(theta).repeat(50).by(gradient).theta();
        System.out.println(decent);
    }
}
