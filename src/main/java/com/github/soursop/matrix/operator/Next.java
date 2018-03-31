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
            return one.asDoubleOperator().toMatrix(another.asDoubleOperator());
        } else if (isOne) {
            return byMatrix(one.asDoubleOperator().toIterator(another.asDoubleMatrix().height()), another.asDoubleMatrix());
        } else if (isAnother) {
            return byMatrix(one.asDoubleMatrix(), another.asDoubleOperator().toIterator(one.asDoubleMatrix().height()));
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
        return invoke(DoubleMatrix.NONE);
    }

    @Override
    public CharSequence asSimple(int depth) {
        return asSimple("::", depth);
    }

    @Override
    public DoubleMatrix asDoubleMatrix() {
        return DoubleMatrix.NONE;
    }

    @Override
    public DoubleOperator asDoubleOperator() {
        return DoubleOperator.NONE;
    }
}
