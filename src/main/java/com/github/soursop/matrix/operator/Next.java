package com.github.soursop.matrix.operator;


public class Next extends AbstractOperators {
    protected Next(Operator ... operators) {
        super(operators);
    }

    private Operator byMatrix(DoubleMatrix one, DoubleMatrix another) {
        return one.isNone()? another : another.isNone()? one : new NextDoubleMatrix(one, another);
    }

    private Operator byNone(Operator base, Operator another) {
        return base.isNone()? another : another.isNone()? base : byOperator(base, another);
    }

    private Operator byOperator(Operator one, Operator another) {
        boolean isOne = one.asDoubleOperator().isSome();
        boolean isAnother = another.asDoubleOperator().isSome();
        if (isOne && isAnother) {
            return new DenseDoubleMatrix(2, one.asDoubleOperator().getValue(), another.asDoubleOperator().getValue());
        } else if (isOne) {
            return byMatrix(one.asDoubleOperator().toIterator(another.asDoubleMatrix().height(), 1), another.asDoubleMatrix());
        } else if (isAnother) {
            return byMatrix(one.asDoubleMatrix(), another.asDoubleOperator().toIterator(one.asDoubleMatrix().height(), 1));
        } else {
            return byMatrix(one.asDoubleMatrix(), another.asDoubleMatrix());
        }
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        Operator base = prev;
        for (Operator op : getOperators()) {
            base = op.asOperators().invoke(base);
            base = byNone(base, op);
        }
        return base.asDoubleMatrix();
    }

    public DoubleMatrix invoke() {
        return invoke(None.DOUBLE_MATRIX);
    }

    @Override
    public CharSequence asSimple(int depth) {
        return asSimple("::", depth);
    }

    @Override
    public DoubleMatrix asDoubleMatrix() {
        return None.DOUBLE_MATRIX;
    }

    @Override
    public DoubleOperator asDoubleOperator() {
        return None.DOUBLE_OPERATOR;
    }
}
