package com.github.soursop.matrix.operator;

class DivideOperators extends LazyOperators implements Sign.Divide {
    protected DivideOperators(AbstractOperators origin) {
        super(origin);
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
