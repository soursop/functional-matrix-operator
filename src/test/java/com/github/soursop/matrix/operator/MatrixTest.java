package com.github.soursop.matrix.operator;

import org.junit.Ignore;
import org.junit.Test;

import static com.github.soursop.matrix.operator.Functions.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MatrixTest {
    private double[] sample = new double[]{ 1d, 2d, 3d, 4d, 5d, 6d };
    private double[] wideSample = new double[]{ 1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d, 11d, 12d };
    private double[] transpose = new double[]{ 1d, 3d, 5d, 2d, 4d, 6d };

    @Ignore
    @Test
    public void testMatrix() {
        DenseDoubleMatrix matrix = DenseDoubleMatrix.of(2, wideSample);
        System.out.println(matrix);
        System.out.println(matrix.transpose());
    }

    @Test
    public void testAppendToHead() {
        DoubleOperator head = DoubleOperator.of(1l);
        DoubleMatrix one = DenseDoubleMatrix.of(2, sample);
        DoubleMatrix another = DenseDoubleMatrix.of(2, sample);
        DoubleMatrix next = head.next(one).next(another).invoke();
        assertThat(asList(next.values()), is(asList(
                1d, 1d, 2d, 1d, 2d
                , 1d, 3d, 4d, 3d, 4d
                , 1d, 5d, 6d, 5d, 6d
        )));
    }

    @Test
    public void testTailToHead() {
        DoubleOperator head = DoubleOperator.of(1l);
        DoubleMatrix one = DenseDoubleMatrix.of(2, sample);
        DoubleMatrix another = DenseDoubleMatrix.of(2, sample);
        DoubleMatrix invoke = head.next(one).next(another).invoke();
        assertThat(asList(invoke.head().values()), is(asList(
                1d
                , 1d
                , 1d
        )));
        assertThat(asList(invoke.tail().values()), is(asList(
                1d, 2d, 1d, 2d
                , 3d, 4d, 3d, 4d
                , 5d, 6d, 5d, 6d
        )));
    }

    @Test
    public void testHeadFromUnder() {
        DoubleOperator head = DoubleOperator.of(1l);
        DoubleMatrix one = DenseDoubleMatrix.of(2, sample);
        DoubleMatrix another = DenseDoubleMatrix.of(2, sample);
        DoubleMatrix invoke = head.under(one).under(another).invoke();
        assertThat(asList(invoke.values()), is(asList(
                1d, 1d
                , 1d, 2d
                , 3d, 4d
                , 5d, 6d
                , 1d, 2d
                , 3d, 4d
                , 5d, 6d
        )));
        assertThat(asList(invoke.head().values()), is(asList(
                1d, 1d, 3d, 5d, 1d, 3d, 5d
        )));
    }

    @Test
    public void testNextTranspose() {
        DoubleOperator head = DoubleOperator.of(1l);
        DoubleMatrix one = DenseDoubleMatrix.of(2, sample);
        DoubleMatrix another = DenseDoubleMatrix.of(2, sample);
        DoubleMatrix transpose = head.next(one).next(another).invoke().transpose();
        assertThat(asList(transpose.values()), is(asList(
        1d, 1d, 1d
            , 1d, 3d, 5d
            , 2d, 4d, 6d
            , 1d, 3d, 5d
            , 2d, 4d, 6d
        )));
    }

    @Test
    public void testNextToLast() {
        DoubleOperator last = DoubleOperator.of(1l);
        DoubleMatrix one = DenseDoubleMatrix.of(2, sample);
        DoubleMatrix next = one.next(last).invoke();
        assertThat(asList(next.values()), is(asList(
                1d, 2d, 1d
                , 3d, 4d, 1d
                , 5d, 6d, 1d
        )));
    }

    @Ignore
    @Test
    public void testAsSimple() {
        DenseDoubleMatrix matrix = DenseDoubleMatrix.of(2, sample);
        DoubleMatrix other = DenseDoubleMatrix.of(3, transpose);
        System.out.println(matrix.product(other).asSimple(0));
    }

    @Test
    public void testTranspose() {
        DoubleMatrix transposed = DenseDoubleMatrix.of(2, sample).transpose();
        DoubleMatrix expect = DenseDoubleMatrix.of(3, transpose);
        for (int i = 0; i < transpose.length; i++) {
            assertThat(transposed.valueOf(i), is(expect.valueOf(i)));
        }
    }
}
