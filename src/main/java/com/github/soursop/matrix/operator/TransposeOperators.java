package com.github.soursop.matrix.operator;

class TransposeOperators extends AbstractOperators implements Sign.Transpose {
    protected TransposeOperators(With with, Operator... operators) {
        super(with, operators);
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        return super.invoke(prev).transpose();
    }

    @Override
    protected CharSequence _asSimple(int depth) {
        return asSimple(Sign.sign(getClass()), depth);
    }
}
