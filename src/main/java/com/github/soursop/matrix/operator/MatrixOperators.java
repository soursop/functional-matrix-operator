package com.github.soursop.matrix.operator;

/**
 * @author soursop
 * @created 2018. 4. 3.
 */
interface MatrixOperators extends Operators {
    DoubleMatrix invoke();
    @Override
    DoubleMatrix invoke(Operator prev);
}
