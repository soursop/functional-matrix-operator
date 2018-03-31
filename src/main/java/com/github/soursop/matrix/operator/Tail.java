package com.github.soursop.matrix.operator;


public class Tail extends AbstractOperators {
    protected Tail(Operator ... operators) {
        super(operators);
    }

    private Operator byMatrix(DoubleMatrix one, DoubleMatrix another) {
        return one.isNone()? another : another.isNone()? one : new TailDoubleMatrix(one, another);
    }

    private Operator byNone(Operator base, Operator another) {
        return base.isNone()? another : another.isNone()? base : byOperator(base, another);
    }

    private Operator byOperator(Operator one, Operator another) {
        boolean isOne = one.asDoubleOperator().isSome();
        boolean isAnother = another.asDoubleOperator().isSome();
        if (isOne && isAnother) {
            return new DenseDoubleMatrix(1, one.asDoubleOperator().getValue(), another.asDoubleOperator().getValue());
        } else if (isOne) {
            return byMatrix(one.asDoubleOperator().toIterator(1, another.asDoubleMatrix().width()), another.asDoubleMatrix());
        } else if (isAnother) {
            return byMatrix(one.asDoubleMatrix(), another.asDoubleOperator().toIterator(1, one.asDoubleMatrix().width()));
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
        return asSimple("||", depth);
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
