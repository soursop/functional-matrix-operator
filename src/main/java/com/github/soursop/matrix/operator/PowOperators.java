package com.github.soursop.matrix.operator;

class PowOperators extends LazyOperators implements Sign.Pow {
    private final int pow;
    protected PowOperators(AbstractOperators origin, int pow) {
        super(origin);
        this.pow = pow;
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        return super.invoke(prev).pow(pow).asDoubleMatrix();
    }

    @Override
    public DoubleMatrix invoke() {
        return super.invoke().pow(pow).asDoubleMatrix();
    }

    @Override
    public CharSequence asSimple(int depth) {
        return super.asSimple(Sign.sign(getClass()), depth);
    }
}
