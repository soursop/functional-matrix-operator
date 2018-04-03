package com.github.soursop.matrix.operator;

public interface None {
    DoubleMatrix DOUBLE_MATRIX = new NoneDoubleMatrix(0,0, new double[0]);
    Operators OPERATORS = new NoneOperators();
    DoubleOperator DOUBLE_OPERATOR = new NoneDoubleOperator(0l);

    class NoneDoubleOperator extends DoubleOperator implements None {
        public NoneDoubleOperator(double value) {
            super(value);
        }
    }

    class NoneDoubleMatrix extends DenseDoubleMatrix implements None {
        public NoneDoubleMatrix(int height, int width, double... values) {
            super(height, width, values);
        }
    }

    class NoneOperators extends AbstractOperators implements None {
        @Override
        public DoubleMatrix asDoubleMatrix() {
            return DOUBLE_MATRIX;
        }

        @Override
        public DoubleOperator asDoubleOperator() {
            return DOUBLE_OPERATOR;
        }

        @Override
        public Operator minus() {
            return this;
        }

        @Override
        public Operator divide() {
            return this;
        }

        @Override
        public String asSimple(int depth) {
            return "";
        }

        @Override
        public Operators asOperators() {
            return OPERATORS;
        }

        @Override
        public Operator invoke(Operator prev) {
            return prev;
        }
    }
}
