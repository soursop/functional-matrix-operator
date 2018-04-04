package com.github.soursop.matrix.operator;

public interface None {
    DoubleMatrix DOUBLE_MATRIX = new NoneDoubleMatrix(0,0, new double[0]);
    DoubleOperator DOUBLE_OPERATOR = new NoneDoubleOperator(0l);
    Operators OPERATORS = new NoneOperators(new Applier() {
        @Override
        public String symbol() {
            return "";
        }

        @Override
        public DoubleMatrix apply(DoubleMatrix one, DoubleMatrix other) {
            return one;
        }

        @Override
        public DoubleMatrix apply(DoubleMatrix one, DoubleOperator other) {
            return one;
        }

        @Override
        public DoubleMatrix apply(DoubleOperator one, DoubleMatrix other) {
            return other;
        }

        @Override
        public Operator apply(DoubleOperator one, DoubleOperator other) {
            return one;
        }
    }, new Operator[0]);

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
        protected NoneOperators(Applier applier, Operator... operators) {
            super(applier, operators);
        }

        @Override
        public DoubleMatrix asDoubleMatrix() {
            return DOUBLE_MATRIX;
        }

        @Override
        public DoubleOperator asDoubleOperator() {
            return DOUBLE_OPERATOR;
        }

        @Override
        public Operators minus() {
            return this;
        }

        @Override
        public Operators divide() {
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
        public DoubleMatrix invoke(Operator prev) {
            return prev.asDoubleMatrix();
        }
    }
}
