package com.github.soursop.matrix.operator;

import org.junit.Ignore;
import org.junit.Test;


import static com.github.soursop.matrix.operator.Functions.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author soursop
 * @created 2018. 4. 3.
 */
public class OperatorsTest {
    private double[] before = new double[]{ 1d, 2d, 3d, 4d, 5d, 6d };
    private double[] after = new double[]{ 7d, 8d, 9d, 10d, 11d, 12d };
    private double[] transpose = new double[]{ 2d, 4d, 6d, 3d, 5d, 7d };

    @Test
    public void testProductWithMatrix() {
        DoubleMatrix one = DenseDoubleMatrix.of(2, before);
        DoubleMatrix other = DenseDoubleMatrix.of(3, transpose);
        DoubleMatrix result = one.product(other).invoke();
        assertThat(asList(result.values()), is(asList(
                1d * 2d + 2d * 3d
                , 1d * 4d + 2d * 5d
                , 1d * 6d + 2d * 7d
                , 3d * 2d + 4d * 3d
                , 3d * 4d + 4d * 5d
                , 3d * 6d + 4d * 7d
                , 5d * 2d + 6d * 3d
                , 5d * 4d + 6d * 5d
                , 5d * 6d + 6d * 7d
        )));
    }

    @Test
    public void testMultiplyWithMatrix() {
        DoubleMatrix one = DenseDoubleMatrix.of(2, before);
        DoubleMatrix result = one.multiply(one).invoke();
        assertThat(asList(result.values()), is(asList(
                1d * 1d
                , 2d * 2d
                , 3d * 3d
                , 4d * 4d
                , 5d * 5d
                , 6d * 6d
        )));
    }

    @Test
    public void testPlusWithMatrix() {
        DoubleMatrix one = DenseDoubleMatrix.of(2, before);
        DoubleMatrix other = DenseDoubleMatrix.of(2, before);
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
        DoubleMatrix one = DenseDoubleMatrix.of(2, before);
        DoubleMatrix other = DenseDoubleMatrix.of(2, before);
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
    public void testDivideWithOperator() {
        DoubleMatrix one = DenseDoubleMatrix.of(2, before);
        DoubleMatrix result = one.divide(new DoubleOperator(2)).invoke();
        assertThat(asList(result.values()), is(asList(
                1d / 2d
                , 2d / 2d
                , 3d / 2d
                , 4d / 2d
                , 5d / 2d
                , 6d / 2d
        )));
    }

    @Test
    public void testMinusWithIdentity() {
        DenseDoubleMatrix one = DenseDoubleMatrix.of(2, before);
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

    @Test
    public void testDivideWithIdentity() {
        DenseDoubleMatrix one = DenseDoubleMatrix.of(2, before);
        DoubleMatrix result = one.divide();
        assertThat(asList(result.values()), is(asList(
                1/1d
                , 1/2d
                , 1/3d
                , 1/4d
                , 1/5d
                , 1/6d
        )));
    }

    @Test
    public void testTranspose() {
        DoubleMatrix one = DenseDoubleMatrix.of(2, before);
        DoubleMatrix other = DenseDoubleMatrix.of(2, after);
        DoubleMatrix result = one.transpose().product(other).invoke();
        assertThat(asList(result.values()), is(asList(
                1d * 7d + 3d * 9d + 5d * 11d
                , 1d * 8d + 3d * 10d + 5d * 12d
                , 2d * 7d + 4d * 9d + 6d * 11d
                , 2d * 8d + 4d * 10d + 6d * 12d
        )));
        DoubleMatrix transpose = one.transpose().product(other).transpose().invoke();
        assertThat(asList(transpose.values()), is(asList(
                1d * 7d + 3d * 9d + 5d * 11d
                , 2d * 7d + 4d * 9d + 6d * 11d
                , 1d * 8d + 3d * 10d + 5d * 12d
                , 2d * 8d + 4d * 10d + 6d * 12d
        )));
    }

    @Ignore
    @Test
    public void testAsSimple() {
        DenseDoubleMatrix one = DenseDoubleMatrix.of(2, before);
        DenseDoubleMatrix other = DenseDoubleMatrix.of(2, before);
        Plus minus = one.divide().minus(other).plus(other);
        System.out.println(minus.asSimple(0));
    }

}
