package com.github.soursop.matrix.operator;


public class Divide extends AbstractOperators {
    protected Divide(Operator... operators) {
        super(operators);
    }

    private DoubleMatrix multiply(DoubleMatrix one, DoubleMatrix other) {
        return withProduct(Sign.DIVIDE, one, other);
    }

    private DoubleMatrix multiply(DoubleMatrix one, double other) {
        return withElement(Sign.DIVIDE, one, other);
    }

    public DoubleMatrix invoke() {
        return invoke(DoubleMatrix.NONE);
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        double product = Sign.DIVIDE.valueOf(prev.asDoubleOperator());
        DoubleMatrix base = prev.asDoubleMatrix();
        for (Operator op : getOperators()) {
            base = op.asOperators().invoke(base);
            base = multiply(base, op.asDoubleMatrix());
            product = Sign.DIVIDE.apply(product, Sign.DIVIDE.valueOf(op.asDoubleOperator()));
        }
        return multiply(base, product);
    }

    @Override
    public CharSequence asSimple(int depth) {
        return asSimple(Sign.DIVIDE.sign, depth);
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
