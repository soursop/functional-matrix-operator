package com.github.soursop.matrix.operator;

class DivideOperators extends AbstractOperators implements Sign.Divide {
    protected DivideOperators(With with, Operator... operators) {
        super(with, operators);
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        return super.invoke(prev).divide().asDoubleMatrix();
    }

    @Override
    protected CharSequence _asSimple(int depth) {
        return asSimple(Sign.sign(getClass()), depth);
    }
}
