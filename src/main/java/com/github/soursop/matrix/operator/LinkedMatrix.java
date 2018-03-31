package com.github.soursop.matrix.operator;

public interface LinkedMatrix<T extends Matrix> extends Matrix {
    T head();
    T tail();
}
