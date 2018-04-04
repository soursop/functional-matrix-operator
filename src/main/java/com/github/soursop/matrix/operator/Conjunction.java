package com.github.soursop.matrix.operator;

enum Conjunction implements Applier {
    NEXT("::") {
        @Override
        protected DoubleMatrix some(DoubleMatrix one, DoubleMatrix other) {
            return new NextDoubleMatrix(one, other);
        }

        @Override
        protected DoubleMatrix asIterator(DoubleOperator one, DoubleMatrix other) {
            return one.toIterator(other.height(), 1);
        }
    }
    , TAIL(";") {
        @Override
        protected DoubleMatrix some(DoubleMatrix one, DoubleMatrix other) {
            return new TailDoubleMatrix(one, other);
        }

        @Override
        protected DoubleMatrix asIterator(DoubleOperator one, DoubleMatrix other) {
            return one.toIterator(1, other.width());
        }
    }
    ;
    public final String sign;
    Conjunction(String sign) {
        this.sign = sign;
    }

    protected abstract DoubleMatrix some(DoubleMatrix one, DoubleMatrix other);
    protected abstract DoubleMatrix asIterator(DoubleOperator one, DoubleMatrix other);

    @Override
    public String symbol() {
        return sign;
    }

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
