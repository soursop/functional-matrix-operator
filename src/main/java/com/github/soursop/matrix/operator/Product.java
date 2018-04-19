package com.github.soursop.matrix.operator;

public class Product extends WithOperators implements Sign.Product {
    protected Product(Operator... operators) {
        super(with, operators);
    }
}