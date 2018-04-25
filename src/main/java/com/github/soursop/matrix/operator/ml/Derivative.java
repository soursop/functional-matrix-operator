package com.github.soursop.matrix.operator.ml;


public interface Derivative<T> extends Assessed<T> {
    T gradient(T thetas);
}
