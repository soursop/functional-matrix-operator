package com.github.soursop.matrix.operator;

abstract class Conjunction implements With {

    protected abstract DoubleMatrix some(DoubleMatrix one, DoubleMatrix other);
    protected abstract DoubleMatrix asIterator(DoubleOperator one, DoubleMatrix other);

    @Override
    public DoubleMatrix apply(DoubleMatrix one, DoubleMatrix other) {
        return other.isNone()? one : (one.isNone()? other : some(one, other));
    }

    @Override
    public DoubleMatrix apply(DoubleMatrix one, DoubleOperator other) {
        return (one.isNone() || other.isNone())? one : some(one, asIterator(other, one));
    }

    @Override
    public DoubleMatrix apply(DoubleOperator one, DoubleMatrix other) {
        return (one.isNone() || other.isNone())? other : some(asIterator(one, other), other);
    }

    @Override
    public DoubleOperator apply(DoubleOperator one, DoubleOperator other) {
        return other.isNone()? one : other;
    }
}
