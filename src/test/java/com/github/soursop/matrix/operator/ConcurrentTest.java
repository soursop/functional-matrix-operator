package com.github.soursop.matrix.operator;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ForkJoinPool;


public class ConcurrentTest {

    @Test
    public void testSplit() {
        double[] wideSample = new double[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        DenseDoubleMatrix doubles = new DenseDoubleMatrix(2, wideSample);
        List<DoubleMatrix> doubleMatrices = doubles.splitBy(2);
        for (DoubleMatrix m : doubleMatrices) {
            System.out.println(m);
        }
    }

    @Ignore
    @Test
    public void testForkAndJoin() {
        int height = 1_000_000;
        int width = 20;
        int size = 30;
        int split = 100_000;

        double[] values1 = new DoubleRandomIterator(height, width, 0).values();
        double[] values2 = new DoubleRandomIterator(width, size, 0).values();
        final DenseDoubleMatrix one = new DenseDoubleMatrix(width, values1);
        final Product product = new Product(new DenseDoubleMatrix(size, values2));

        long s1 = System.currentTimeMillis();
        DoubleMatrix resultAll = product.invoke(one);
        System.out.println("single result size: " + resultAll.size());
        System.out.println("single elaps time: " + (System.currentTimeMillis() - s1));

        long s2 = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        DoubleMatrix resultBySplit = pool.invoke(new SplitDoubleMatrix(one, product, split));
        System.out.println("thread result size: " + resultBySplit.size());
        System.out.println("thread elaps time: " + (System.currentTimeMillis() - s2));
    }
}
