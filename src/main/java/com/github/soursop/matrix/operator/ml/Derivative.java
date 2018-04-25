package com.github.soursop.matrix.operator.ml;


public interface Derivative<T> {
    Assessed<T> init(T theta);
    Assessed<T> gradient(Assessed<T> cost);
    double cost(T theta);
}
