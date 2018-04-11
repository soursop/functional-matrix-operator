package com.github.soursop.matrix.operator;

class FunctionOperators extends LazyOperators implements Sign.Transpose {
    private final Function function;
    protected FunctionOperators(Function function, AbstractOperators origin) {
        super(origin);
        this.function = function;
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        return super.invoke(prev).apply(function).asDoubleMatrix();
    }

    @Override
    public DoubleMatrix invoke() {
        return super.invoke().apply(function).asDoubleMatrix();
    }

    @Override
    protected CharSequence _asSimple(int depth) {
        return asSimple(Sign.sign(getClass()), depth);
    }
}
