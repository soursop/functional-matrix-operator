package com.github.soursop.matrix.operator;

import java.util.List;

abstract class AbstractOperators extends AbstractOperator implements Operators {
    private final List<Operator> operators;

    protected AbstractOperators(List<Operator> operators) {
        this.operators = operators;
    }

    @Override
    public List<Operator> getOperators() {
        return operators;
    }

    @Override
    public Operators asOperators() {
        return this;
    }

    @Override
    public Operator invoke(Operator prev) {
        return prev;
    }

    @Override
    public void add(Operator operator) {
        operators.add(operator);
    }

    protected DoubleMatrix assign(Sign sign, DoubleMatrix one, DoubleMatrix other) {
        return one.isNone()? other : other.isNone()? one : asAssign(sign, one, other);
    }

    private DoubleMatrix asAssign(Sign sign, DoubleMatrix one, DoubleMatrix other) {
        double[] values = new double[one.height() * other.width()];
        for (int i = 0; i < one.size(); i++) {
            values[i] = sign.apply(one.valueOf(i), other.valueOf(i));
        }
        return new DenseDoubleMatrix(other.width(), values);
    }

    protected DoubleMatrix assign(Sign sign, DoubleMatrix one, double other) {
        return one.isNone()? one : other == 1l? one : asAssign(sign, one, other);
    }

    private DoubleMatrix asAssign(Sign sign, DoubleMatrix one, double other) {
        double[] values = new double[one.height() * one.width()];
        for (int i = 0; i < one.size(); i++) {
            values[i] = sign.apply(one.valueOf(i), other);
        }
        return new DenseDoubleMatrix(one.width(), values);
    }

    protected DoubleMatrix product(Sign sign, DoubleMatrix one, DoubleMatrix other) {
        return one.isNone()? other : other.isNone()? one : asProduct(sign, one, other);
    }

    private DoubleMatrix asProduct(Sign sign, DoubleMatrix one, DoubleMatrix other) {
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

    protected String asSimple(String sign, int depth) {
        StringBuilder builder = withPadding(depth);
        List<Operator> operators = getOperators();
        if (operators.size() > 1) {
            builder.append("(");
        }
        for (int i = 0; i < operators.size(); i++) {
            builder.append(operators.get(i).asSimple(depth + 1));
            if (i + 1 < operators.size()) {
                builder.append(sign);
            }
        }
        if (operators.size() > 1) {
            builder.append(")");
        }
        return builder.toString();
    }
}
