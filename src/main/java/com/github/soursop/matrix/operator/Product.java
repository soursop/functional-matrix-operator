package com.github.soursop.matrix.operator;

public class Product extends WithOperators implements Sign.Product {
    public Product(Operator... operators) {
        super(with, operators);
    }
}