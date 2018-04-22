package com.github.soursop.matrix.operator;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MatrixTest {
    private double[] sample = new double[]{ 1l, 2l, 3l, 4l, 5l, 6l };
    private double[] wideSample = new double[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
    private double[] transpose = new double[]{ 1l, 3l, 5l, 2l, 4l, 6l };

    @Ignore
    @Test
    public void testMatrix() {
        DenseDoubleMatrix matrix = new DenseDoubleMatrix(2, wideSample);
        System.out.println(matrix);
        System.out.println(matrix.transpose());
    }

    @Ignore
    @Test
    public void testAppendToHead() {
        DoubleOperator head = DoubleOperator.of(1l);
        DoubleMatrix one = new DenseDoubleMatrix(2, sample);
        DoubleMatrix another = new DenseDoubleMatrix(2, sample);
        System.out.println(head.next(one).next(another).invoke());
    }

    @Ignore
    @Test
    public void testTailToHead() {
        DoubleOperator head = DoubleOperator.of(1l);
        DoubleMatrix one = new DenseDoubleMatrix(2, sample);
        DoubleMatrix another = new DenseDoubleMatrix(2, sample);
        DoubleMatrix invoke = head.next(one).next(another).invoke();
        System.out.println(invoke.head());
    }

    @Ignore
    @Test
    public void testHeadFromUnder() {
        DoubleOperator head = DoubleOperator.of(1l);
        DoubleMatrix one = new DenseDoubleMatrix(2, sample);
        DoubleMatrix another = new DenseDoubleMatrix(2, sample);
        DoubleMatrix invoke = head.under(one).under(another).invoke();
        System.out.println(invoke.head());
    }

    @Ignore
    @Test
    public void testNextTranspose() {
        DoubleOperator head = DoubleOperator.of(1l);
        DoubleMatrix one = new DenseDoubleMatrix(2, sample);
        DoubleMatrix another = new DenseDoubleMatrix(2, sample);
        System.out.println(head.next(one).next(another).invoke().transpose());
    }

    @Test
    public void testNextToLast() {
        DoubleOperator last = DoubleOperator.of(1l);
        DoubleMatrix one = new DenseDoubleMatrix(2, sample);
        System.out.println(one.next(last).invoke());
    }

    @Ignore
    @Test
    public void testAsSimple() {
        DenseDoubleMatrix matrix = new DenseDoubleMatrix(2, sample);
        DoubleMatrix other = new DenseDoubleMatrix(3, transpose);
        System.out.println(matrix.product(other).asSimple(0));
    }

    @Test
    public void testTranspose() {
        DoubleMatrix transposed = new DenseDoubleMatrix(2, sample).transpose();
        DoubleMatrix expect = new DenseDoubleMatrix(3, transpose);
        for (int i = 0; i < transpose.length; i++) {
            assertThat(transposed.valueOf(i), is(expect.valueOf(i)));
        }
    }
}
