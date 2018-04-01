package com.github.soursop.matrix.operator;


public class Plus extends AbstractOperators {
    protected Plus(Operator... operators) {
        super(operators);
    }

    private DoubleMatrix plus(DoubleMatrix one, DoubleMatrix other) {
        return withProduct(Sign.PLUS, one, other);
    }

    private DoubleMatrix plus(DoubleMatrix one, double other) {
        return withElement(Sign.PLUS, one, other);
    }

    public DoubleMatrix invoke() {
        return invoke(DoubleMatrix.NONE);
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        double product = Sign.PLUS.valueOf(prev.asDoubleOperator());
        DoubleMatrix base = prev.asDoubleMatrix();
        for (Operator op : getOperators()) {
            base = op.asOperators().invoke(base);
            base = plus(base, op.asDoubleMatrix());
            product = Sign.PLUS.apply(product, Sign.PLUS.valueOf(op.asDoubleOperator()));
        }
        return plus(base, product);
    }

    @Override
    public CharSequence asSimple(int depth) {
        return asSimple(Sign.PLUS.sign, depth);
    }

    @Override
    public DoubleMatrix asDoubleMatrix() {
        return DoubleMatrix.NONE;
    }

    @Override
    public DoubleOperator asDoubleOperator() {
        return DoubleOperator.NONE;
    }
}
