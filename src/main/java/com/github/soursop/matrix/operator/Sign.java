package com.github.soursop.matrix.operator;

public enum Sign {
    MULTIPLY("*") {
        @Override
        double apply(double v1, double v2) {
            return v1 * v2;
        }

        @Override
        protected DoubleMatrix some(DoubleMatrix one, DoubleMatrix other) {
            return innerProduct(this, one, other);
        }

        @Override
        protected DoubleMatrix none(DoubleMatrix none, DoubleMatrix some) {
            return some;
        }

        @Override
        protected DoubleOperator some(DoubleOperator one, DoubleOperator other) {
            return new DoubleOperator(apply(one.getValue(), other.getValue()));
        }

        @Override
        protected DoubleOperator none(DoubleOperator none, DoubleOperator some) {
            return some;
        }
    }
    , PLUS("+") {
        @Override
        double apply(double v1, double v2) {
            return v1 + v2;
        }

        @Override
        protected DoubleMatrix some(DoubleMatrix one, DoubleMatrix other) {
            return elementWise(this, one, other);
        }

        @Override
        protected DoubleMatrix none(DoubleMatrix none, DoubleMatrix some) {
            return some;
        }

        @Override
        protected DoubleOperator some(DoubleOperator one, DoubleOperator other) {
            return new DoubleOperator(apply(one.getValue(), other.getValue()));
        }

        @Override
        protected DoubleOperator none(DoubleOperator none, DoubleOperator some) {
            return some;
        }
    }
    ;
    public final String sign;
    Sign(String sign) {
        this.sign = sign;
    }

    abstract double apply(double v1, double v2);
    protected abstract DoubleMatrix some(DoubleMatrix one, DoubleMatrix other);
    protected abstract DoubleMatrix none(DoubleMatrix none, DoubleMatrix some);
    protected abstract DoubleOperator some(DoubleOperator one, DoubleOperator other);
    protected abstract DoubleOperator none(DoubleOperator none, DoubleOperator some);

    public DoubleMatrix apply(DoubleMatrix one, DoubleMatrix other) {
        return other.isNone()? (one.isNone()? one : none(other, one)) : (one.isNone()? none(one, other) : some(one, other));
    }

    public DoubleMatrix apply(DoubleMatrix one, DoubleOperator other) {
        return other.isNone()? one : one.isNone()? one : elementWise(this, one, other);
    }

    public DoubleOperator apply(DoubleOperator one, DoubleOperator other) {
        return other.isNone()? (one.isNone()? one : none(other, one)) : (one.isNone()? none(one, other) : some(one, other));
    }

    protected DoubleMatrix elementWise(Sign sign, DoubleMatrix one, DoubleOperator other) {
        double[] values = new double[one.height() * one.width()];
        for (int i = 0; i < one.size(); i++) {
            values[i] = sign.apply(one.valueOf(i), other.getValue());
        }
        return new DenseDoubleMatrix(one.width(), values);
    }

    protected DoubleMatrix elementWise(Sign sign, DoubleMatrix one, DoubleMatrix other) {
        Assert.assertElementSize(sign, one, other);
        double[] values = new double[one.height() * other.width()];
        for (int i = 0; i < one.size(); i++) {
            values[i] = sign.apply(one.valueOf(i), other.valueOf(i));
        }
        return new DenseDoubleMatrix(other.width(), values);
    }

    protected DoubleMatrix innerProduct(Sign sign, DoubleMatrix one, DoubleMatrix other) {
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
