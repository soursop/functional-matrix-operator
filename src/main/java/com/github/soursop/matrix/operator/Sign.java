package com.github.soursop.matrix.operator;

public enum Sign {
    MULTIPLY("*") {
        @Override
        double apply(double v1, double v2) {
            return v1 * v2;
        }
    }
    ;

    public final String sign;
    Sign(String sign) {
        this.sign = sign;
    }

    abstract double apply(double v1, double v2);
}
