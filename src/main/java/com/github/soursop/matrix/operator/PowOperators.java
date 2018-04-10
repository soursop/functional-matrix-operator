package com.github.soursop.matrix.operator;

class PowOperators extends AbstractOperators implements Sign.Pow {
    private final int pow;
    protected PowOperators(int pow,  With with, Operator... operators) {
        super(with, operators);
        this.pow = pow;
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        return super.invoke(prev).pow(pow).asDoubleMatrix();
    }
}
