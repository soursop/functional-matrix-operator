package com.github.soursop.matrix.operator;

class MinusOperators extends AbstractOperators implements Sign.Minus {
    protected MinusOperators(With with, Operator... operators) {
        super(with, operators);
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        return super.invoke(prev).minus().asDoubleMatrix();
    }

    @Override
    protected CharSequence _asSimple(int depth) {
        return asSimple(Sign.sign(getClass()), depth);
    }

}
