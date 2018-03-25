package com.github.soursop.matrix.operator;

import java.util.List;

public class Append extends AbstractOperators {
    protected Append(List<Operator> operators) {
        super(operators);
    }

    private DoubleMatrix asAppend(DoubleMatrix one, DoubleMatrix another) {
        int width = one.width() + another.width();
        double[] doubles = new double[width * one.height()];
        int i = 0;
        for (int h = 0; h < one.height(); h++) {
            for (int o = 0; o < one.width(); o++) {
                doubles[i++] = one.valueOf(h, o);
            }
            for (int a = 0; a < another.width();a++) {
                doubles[i++] = another.valueOf(h, a);
            }
        }
        return new DoubleMatrix(width, doubles);
    }

    private DoubleMatrix append(DoubleMatrix base, DoubleMatrix matrix) {
        return base.isNone()? matrix : matrix.isNone()? base : asAppend(base, matrix);
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        DoubleMatrix base = prev.asDoubleMatrix();
        List<Operator> operators = getOperators();
        for (int i = operators.size() - 1; i > -1; i--) {
            base = operators.get(i).asOperators().invoke(base);
            base = append(operators.get(i).asDoubleMatrix(), base);
            base = append(operators.get(i).asDoubleOperator().toVector(base.height()), base);
        }
        return base;
    }

    public DoubleMatrix invoke() {
        return invoke(DoubleMatrix.NONE);
    }

    @Override
    public CharSequence asSimple(int depth) {
        return asSimple("::", depth);
    }
}
