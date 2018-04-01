package com.github.soursop.matrix.operator;


public class Minus extends AbstractOperators {
    protected Minus(Operator... operators) {
        super(operators);
    }

    private DoubleMatrix minus(DoubleMatrix one, DoubleMatrix other) {
        return withProduct(Sign.MINUS, one, other);
    }

    private DoubleMatrix minus(DoubleMatrix one, double other) {
        return withElement(Sign.MINUS, one, other);
    }

    public DoubleMatrix invoke() {
        return invoke(DoubleMatrix.NONE);
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        double product = Sign.MINUS.valueOf(prev.asDoubleOperator());
        DoubleMatrix base = prev.asDoubleMatrix();
        for (Operator op : getOperators()) {
            base = op.asOperators().invoke(base);
            base = minus(base, op.asDoubleMatrix());
            product = Sign.MINUS.apply(product, Sign.MINUS.valueOf(op.asDoubleOperator()));
        }
        return minus(base, product);
    }

    @Override
    public CharSequence asSimple(int depth) {
        return asSimple(Sign.MINUS.sign, depth);
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
