package com.github.soursop.matrix.operator;

abstract class Calculation implements With {

    abstract double apply(double v1, double v2);
    protected abstract DoubleMatrix some(DoubleMatrix one, DoubleMatrix other);
    protected abstract DoubleMatrix none(DoubleMatrix none, DoubleMatrix some);
    protected abstract DoubleOperator some(DoubleOperator one, DoubleOperator other);
    protected abstract DoubleOperator none(DoubleOperator none, DoubleOperator some);

    @Override
    public DoubleMatrix apply(DoubleMatrix one, DoubleMatrix other) {
        return other.isNone()? (one.isNone()? one : none(other, one)) : (one.isNone()? none(one, other) : some(one, other));
    }

    @Override
    public DoubleMatrix apply(DoubleMatrix one, DoubleOperator other) {
        return other.isNone()? one : one.isNone()? one : elementWise(this, one, other);
    }

    @Override
    public DoubleMatrix apply(DoubleOperator one, DoubleMatrix other) {
        return apply(other, one);
    }

    @Override
    public DoubleOperator apply(DoubleOperator one, DoubleOperator other) {
        return other.isNone()? (one.isNone()? one : none(other, one)) : (one.isNone()? none(one, other) : some(one, other));
    }

    protected DoubleMatrix elementWise(Calculation sign, DoubleMatrix one, DoubleOperator other) {
        double[] values = new double[one.height() * one.width()];
        for (int i = 0; i < one.size(); i++) {
            values[i] = sign.apply(one.valueOf(i), other.getValue());
        }
        return new DenseDoubleMatrix(one.width(), values);
    }

    protected DoubleMatrix elementWise(Calculation sign, DoubleMatrix one, DoubleMatrix other) {
        Assert.assertElementSize(sign, one, other);
        double[] values = new double[one.height() * other.width()];
        for (int i = 0; i < one.size(); i++) {
            values[i] = sign.apply(one.valueOf(i), other.valueOf(i));
        }
        return new DenseDoubleMatrix(other.width(), values);
    }

    protected DoubleMatrix innerProduct(Calculation sign, DoubleMatrix one, DoubleMatrix other) {
        Assert.assertProductSize(sign, one, other);
        double[] values = new double[one.height() * other.width()];
        for (int h = 0; h < one.height(); h++) {
            for (int by = 0; by < other.width(); by++) {
                for (int w = 0; w < one.width(); w++) {
                    values[h * other.width() + by] += sign.apply(one.valueOf(h, w), other.valueOf(w, by));
                }
            }
        }
        return new DenseDoubleMatrix(other.width(), values);
    }
}
