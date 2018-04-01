package com.github.soursop.matrix.operator;

public enum Sign {
    MULTIPLY("*") {
        @Override
        double apply(double v1, double v2) {
            return v1 * v2;
        }

        @Override
        double valueOf(DoubleOperator operator) {
            return operator.isNone()? 1d : operator.getValue();
        }
    }
    , PLUS("+") {
        @Override
        double apply(double v1, double v2) {
            return v1 + v2;
        }

        @Override
        double valueOf(DoubleOperator operator) {
            return operator.isNone()? 0d : operator.getValue();
        }
    }
    , MINUS("-") {
        @Override
        double apply(double v1, double v2) {
            return v1 - v2;
        }

        @Override
        double valueOf(DoubleOperator operator) {
            return operator.isNone()? 0d : operator.getValue();
        }
    }
    , DIVIDE("/") {
        @Override
        double apply(double v1, double v2) {
            return v1 / v2;
        }

        @Override
        double valueOf(DoubleOperator operator) {
            return operator.isNone()? 1d : operator.getValue();
        }
    }
    ;

    public final String sign;
    Sign(String sign) {
        this.sign = sign;
    }

    abstract double apply(double v1, double v2);
    abstract double valueOf(DoubleOperator operator);
}
