package com.github.soursop.matrix.operator.ml;

/**
 * @author soursop
 * @created 2018. 4. 25.
 */
public interface Assessed<T> {
    double cost();
    T theta();
    void cost(double cost);
    void theta(T theta);
}
