package com.github.soursop.matrix.operator;


public class Multiply extends AbstractOperators {
    protected Multiply(Operator... operators) {
        super(operators);
    }

    private DoubleMatrix multiply(DoubleMatrix one, DoubleMatrix other) {
        return product(Sign.MULTIPLY, one, other);
    }

    private DoubleMatrix multiply(DoubleMatrix one, double other) {
        return assign(Sign.MULTIPLY, one, other);
    }

    public DoubleMatrix invoke() {
        return invoke(DoubleMatrix.NONE);
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        double product = prev.asDoubleOperator().getValue();
        DoubleMatrix base = prev.asDoubleMatrix();
        for (Operator op : getOperators()) {
            base = op.asOperators().invoke(base);
            base = multiply(base, op.asDoubleMatrix());
            product = Sign.MULTIPLY.apply(product, op.asDoubleOperator().getValue());
        }
        return multiply(base, product);
    }

    @Override
    public CharSequence asSimple(int depth) {
        return asSimple(Sign.MULTIPLY.sign, depth);
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
