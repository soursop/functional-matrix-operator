package com.github.soursop.matrix.operator;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


public class SplitTest {

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
        int height = 1000_000;
        int width = 20;
        int size = 30;
        int split = 100_000;

        double[] values1 = new DoubleRandomIterator(height, width, 0).values();
        double[] values2 = new DoubleRandomIterator(width, size, 0).values();
        final DenseDoubleMatrix one = new DenseDoubleMatrix(width, values1);
        final Multiply multiply = new Multiply(new DenseDoubleMatrix(size, values2));

        long s1 = System.currentTimeMillis();
        DoubleMatrix resultAll = multiply.invoke(one);
        System.out.println("single result size: " + resultAll.size());
        System.out.println("single elaps time: " + (System.currentTimeMillis() - s1));

        long s2 = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        DoubleMatrix resultBySplit = pool.invoke(new SplitMatrix(one, multiply, split));
        System.out.println("thread result size: " + resultBySplit.size());
        System.out.println("thread elaps time: " + (System.currentTimeMillis() - s2));
    }

    class SplitMatrix extends RecursiveTask<DoubleMatrix> {
        private final int size;
        private final DoubleMatrix origin;
        private final Multiply multiply;
        SplitMatrix(DoubleMatrix origin, Multiply multiply, int size) {
            this.origin = origin;
            this.multiply = multiply;
            this.size = size;
        }


        @Override
        protected DoubleMatrix compute() {
            if (origin.height() > size) {
                return combine(ForkJoinTask.invokeAll(create()));
            } else {
                return invoke(origin);
            }
        }

        private DoubleMatrix combine(Collection<SplitMatrix> results) {
            DoubleMatrix[] result = new DoubleMatrix[results.size()];
            int i = 0;
            for (SplitMatrix split : results) {
                result[i++] = split.join();
            }
            return new Tail(result).invoke();
        }

        private List<SplitMatrix> create() {
            List<DoubleMatrix> doubleMatrices = origin.splitBy(size);
            ArrayList<SplitMatrix> list = new ArrayList<>();
            for (DoubleMatrix doubleMatrix : doubleMatrices) {
                list.add(new SplitMatrix(doubleMatrix, multiply, size));
            }
            return list;
        }

        private DoubleMatrix invoke(DoubleMatrix matrix) {
            return multiply.invoke(matrix);
        }

    }
}
