package com.github.soursop.matrix.operator;

abstract class AbstractSignOperators extends AbstractDoubleMatrixOperators {
    private final Sign sign;
    protected AbstractSignOperators(Sign sign, Operator... operators) {
        super(operators);
        this.sign = sign;
    }

    @Override
    public DoubleMatrix invoke() {
        return invoke(None.DOUBLE_MATRIX);
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        DoubleOperator operator = prev.asDoubleOperator();
        DoubleMatrix base = prev.asDoubleMatrix();
        for (Operator op : getOperators()) {
            base = op.asOperators().invoke(base);
            base = sign.apply(base, op.asDoubleMatrix());
            operator = sign.apply(operator, op.asDoubleOperator());
        }
        return sign.apply(base, operator);
    }

    @Override
    public CharSequence asSimple(int depth) {
        return asSimple(sign.sign, depth);
    }

}
