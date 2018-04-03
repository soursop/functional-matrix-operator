package com.github.soursop.matrix.operator;

abstract class AbstractDoubleMatrixOperators extends AbstractOperators implements MatrixOperators {
    AbstractDoubleMatrixOperators(Operator... operators) {
        super(operators);
    }

    @Override
    public MatrixOperators minus() {
        return MinusDoubleMatrixInvoker.of(this);
    }

    @Override
    public MatrixOperators divide() {
        return DivideDoubleMatrixInvoker.of(this);
    }


    @Override
    public DoubleMatrix asDoubleMatrix() {
        return None.DOUBLE_MATRIX;
    }

    @Override
    public DoubleOperator asDoubleOperator() {
        return None.DOUBLE_OPERATOR;
    }

}
