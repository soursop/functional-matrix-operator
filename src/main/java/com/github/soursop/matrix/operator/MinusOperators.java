package com.github.soursop.matrix.operator;

class MinusOperators extends LazyOperators implements Sign.Minus {
    protected MinusOperators(AbstractOperators origin) {
        super(origin);
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        return super.invoke(prev).minus().asDoubleMatrix();
    }

    @Override
    public DoubleMatrix invoke() {
        return super.invoke().minus().asDoubleMatrix();
    }

    @Override
    public CharSequence asSimple(int depth) {
        return super.asSimple(Sign.sign(getClass()), depth);
    }

}
