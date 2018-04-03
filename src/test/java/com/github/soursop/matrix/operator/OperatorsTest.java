package com.github.soursop.matrix.operator;

import org.junit.Test;


import static com.github.soursop.matrix.operator.Utils.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author soursop
 * @created 2018. 4. 3.
 */
public class OperatorsTest {
    private double[] sample = new double[]{ 1l, 2l, 3l, 4l, 5l, 6l };
    private double[] transpose = new double[]{ 1l, 3l, 5l, 2l, 4l, 6l };

    @Test
    public void testMultiplyWithMatrix() {
        Matrix one = new DenseDoubleMatrix(2, sample);
        Matrix other = new DenseDoubleMatrix(3, transpose);
        DoubleMatrix result = one.multiply(other).invoke();
        assertThat(asList(result.values()), is(asList(
                1d * 1d + 2d * 2d
                , 1d * 3d + 2d * 4d
                , 1d * 5d + 2d * 6d
                , 3d * 1d + 4d * 2d
                , 3d * 3d + 4d * 4d
                , 3d * 5d + 4d * 6d
                , 5d * 1d + 6d * 2d
                , 5d * 3d + 6d * 4d
                , 5d * 5d + 6d * 6d
        )));
    }

    @Test
    public void testPlusWithMatrix() {
        Matrix one = new DenseDoubleMatrix(2, sample);
        Matrix other = new DenseDoubleMatrix(2, sample);
        DoubleMatrix result = one.plus(other).invoke();
        assertThat(asList(result.values()), is(asList(
                1d + 1d
                , 2d + 2d
                , 3d + 3d
                , 4d + 4d
                , 5d + 5d
                , 6d + 6d
        )));
    }

    @Test
    public void testMinusWithMatrix() {
        Matrix one = new DenseDoubleMatrix(2, sample);
        Matrix other = new DenseDoubleMatrix(2, sample);
        DoubleMatrix result = one.minus(other).invoke();
        assertThat(asList(result.values()), is(asList(
                1d - 1d
                , 2d - 2d
                , 3d - 3d
                , 4d - 4d
                , 5d - 5d
                , 6d - 6d
        )));
    }

    @Test
    public void testMinusWithIdentity() {
        DenseDoubleMatrix one = new DenseDoubleMatrix(2, sample);
        DoubleMatrix result = one.minus();
        assertThat(asList(result.values()), is(asList(
                -1d
                , -2d
                , -3d
                , -4d
                , -5d
                , -6d
        )));
    }
}
