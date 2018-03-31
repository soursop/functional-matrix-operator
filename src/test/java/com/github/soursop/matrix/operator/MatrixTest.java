package com.github.soursop.matrix.operator;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.github.soursop.matrix.operator.Utils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MatrixTest {
    private double[] sample = new double[]{ 1l, 2l, 3l, 4l, 5l, 6l };
    private double[] wideSample = new double[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
    private double[] transpose = new double[]{ 1l, 3l, 5l, 2l, 4l, 6l };
    private List<Double> multiply = Arrays.asList(
            1d * 1d + 2d * 2d
            , 1d * 3d + 2d * 4d
            , 1d * 5d + 2d * 6d
            , 3d * 1d + 4d * 2d
            , 3d * 3d + 4d * 4d
            , 3d * 5d + 4d * 6d
            , 5d * 1d + 6d * 2d
            , 5d * 3d + 6d * 4d
            , 5d * 5d + 6d * 6d
    )
    ;

    @Ignore
    @Test
    public void testMatrix() {
        DenseDoubleMatrix matrix = new DenseDoubleMatrix(2, wideSample);
        System.out.println(matrix);
        System.out.println(matrix.transpose());
    }

    @Test
    public void testMultiply() {
        Matrix one = new DenseDoubleMatrix(2, sample);
        Matrix other = new DenseDoubleMatrix(3, transpose);
        DoubleMatrix result = one.multiply(other).invoke();
        assertThat(asList(result.values()), is(multiply));
    }

    @Ignore
    @Test
    public void testAppendToHead() {
        DoubleOperator head = DoubleOperator.of(1l);
        Matrix one = new DenseDoubleMatrix(2, sample);
        Matrix another = new DenseDoubleMatrix(2, sample);
        System.out.println(head.next(one).next(another).invoke());
    }

    @Ignore
    @Test
    public void testHead() {
        DoubleOperator head = DoubleOperator.of(1l);
        Matrix one = new DenseDoubleMatrix(2, sample);
        Matrix another = new DenseDoubleMatrix(2, sample);
        DoubleMatrix invoke = head.next(one).next(another).invoke();
        System.out.println(invoke.head().head());
    }

    @Ignore
    @Test
    public void testAppendTranspose() {
        DoubleOperator head = DoubleOperator.of(1l);
        Matrix one = new DenseDoubleMatrix(2, sample);
        Matrix another = new DenseDoubleMatrix(2, sample);
        System.out.println(head.next(one).next(another).invoke().transpose());
    }

    @Test
    public void testAppendToTail() {
        DoubleOperator last = DoubleOperator.of(1l);
        Matrix one = new DenseDoubleMatrix(2, sample);
        System.out.println(one.next(last).invoke());
    }

    @Ignore
    @Test
    public void testAsSimple() {
        DenseDoubleMatrix matrix = new DenseDoubleMatrix(2, sample);
        Matrix other = new DenseDoubleMatrix(3, transpose);
        System.out.println(matrix.multiply(other).asSimple(0));
    }

    @Test
    public void testTranspose() {
        Matrix transposed = new DenseDoubleMatrix(2, sample).transpose();
        Matrix expect = new DenseDoubleMatrix(3, transpose);
        for (int i = 0; i < transpose.length; i++) {
            assertThat(transposed.valueOf(i), is(expect.valueOf(i)));
        }
    }
}
