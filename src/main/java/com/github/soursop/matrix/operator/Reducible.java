package com.github.soursop.matrix.operator;

/**
 * @author soursop
 * @created 2018. 4. 17.
 */
public interface Reducible {
    SumOperator sum();
    AvgOperator avg();
    StdOperator std();
}
