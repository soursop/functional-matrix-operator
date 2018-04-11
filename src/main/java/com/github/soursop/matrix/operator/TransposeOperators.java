package com.github.soursop.matrix.operator;

class TransposeOperators extends LazyOperators implements Sign.Transpose {
    protected TransposeOperators(AbstractOperators origin) {
        super(origin);
    }

    @Override
    public DoubleMatrix invoke() {
        return super.invoke().transpose();
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        return super.invoke(prev).transpose();
    }

    @Override
    protected CharSequence _asSimple(int depth) {
        return super.asSimple(Sign.sign(getClass()), depth);
    }
}
