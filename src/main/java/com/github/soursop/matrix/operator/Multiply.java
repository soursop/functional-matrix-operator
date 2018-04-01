package com.github.soursop.matrix.operator;


public class Multiply extends AbstractOperators {
    protected Multiply(Operator... operators) {
        super(operators);
    }

    private DoubleMatrix multiply(DoubleMatrix one, DoubleMatrix other) {
        return withProduct(Sign.MULTIPLY, one, other);
    }

    private DoubleMatrix multiply(DoubleMatrix one, double other) {
        return withElement(Sign.MULTIPLY, one, other);
    }

    public DoubleMatrix invoke() {
        return invoke(DoubleMatrix.NONE);
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        double product = Sign.MULTIPLY.valueOf(prev.asDoubleOperator());
        DoubleMatrix base = prev.asDoubleMatrix();
        for (Operator op : getOperators()) {
            base = op.asOperators().invoke(base);
            base = multiply(base, op.asDoubleMatrix());
            product = Sign.MULTIPLY.apply(product, Sign.MULTIPLY.valueOf(op.asDoubleOperator()));
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
