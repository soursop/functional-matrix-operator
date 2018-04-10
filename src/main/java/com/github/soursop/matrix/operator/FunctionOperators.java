package com.github.soursop.matrix.operator;

class FunctionOperators extends AbstractOperators implements Sign.Transpose {
    private final Function function;
    protected FunctionOperators(Function function, With with, Operator... operators) {
        super(with, operators);
        this.function = function;
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        return super.invoke(prev).apply(function).asDoubleMatrix();
    }

    @Override
    protected CharSequence _asSimple(int depth) {
        return asSimple(Sign.sign(getClass()), depth);
    }
}
