package com.github.soursop.matrix.operator.ml;


public interface Assessed<T> {
    double cost(T theta);
}
