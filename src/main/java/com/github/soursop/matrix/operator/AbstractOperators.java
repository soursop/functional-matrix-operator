package com.github.soursop.matrix.operator;

import java.util.Iterator;
import java.util.Stack;

abstract class AbstractOperators extends AbstractOperator implements Operators {
    private final Operator[] operators;

    protected AbstractOperators(Operator... operators) {
        this.operators = operators;
    }

    @Override
    public Operator[] getOperators() {
        return operators;
    }

    @Override
    public Operators asOperators() {
        return this;
    }

    @Override
    public Operator invoke(Operator prev) {
        return prev;
    }

    protected DoubleMatrix withElement(Sign sign, DoubleMatrix one, DoubleMatrix other) {
        return one.isNone()? other : other.isNone()? one : asWithElement(sign, one, other);
    }

    private DoubleMatrix asWithElement(Sign sign, DoubleMatrix one, DoubleMatrix other) {
        double[] values = new double[one.height() * other.width()];
        for (int i = 0; i < one.size(); i++) {
            values[i] = sign.apply(one.valueOf(i), other.valueOf(i));
        }
        return new DenseDoubleMatrix(other.width(), values);
    }

    protected DoubleMatrix withElement(Sign sign, DoubleMatrix one, double other) {
        return one.isNone()? one : other == 1l? one : asWithElement(sign, one, other);
    }

    private DoubleMatrix asWithElement(Sign sign, DoubleMatrix one, double other) {
        double[] values = new double[one.height() * one.width()];
        for (int i = 0; i < one.size(); i++) {
            values[i] = sign.apply(one.valueOf(i), other);
        }
        return new DenseDoubleMatrix(one.width(), values);
    }

    protected DoubleMatrix withProduct(Sign sign, DoubleMatrix one, DoubleMatrix other) {
        return one.isNone()? other : other.isNone()? one : asWithProduct(sign, one, other);
    }

    private DoubleMatrix asWithProduct(Sign sign, DoubleMatrix one, DoubleMatrix other) {
        double[] values = new double[one.height() * other.width()];
        for (int h = 0; h < one.height(); h++) {
            for (int by = 0; by < other.width(); by++) {
                for (int w = 0; w < one.width(); w++) {
                    values[h * other.width() + by] += sign.apply(one.valueOf(h, w), other.valueOf(w, by));
                }
            }
        }
        return new DenseDoubleMatrix(other.width(), values);
    }

    protected String asSimple(String sign, int depth) {
        StringBuilder builder = withPadding(depth);
        Operator[] operators = getOperators();
        if (operators.length > 1) {
            builder.append("(");
        }
        for (int i = 0; i < operators.length; i++) {
            builder.append(operators[i].asSimple(depth + 1));
            if (i + 1 < operators.length) {
                builder.append(sign);
            }
        }
        if (operators.length > 1) {
            builder.append(")");
        }
        return builder.toString();
    }

    @Override
    public Iterator<Operator> iterator() {
        Iterator<Operator> iterator = new Iterator<Operator>() {
            private int pos = 0;

            public boolean hasNext() {
                return operators.length > pos;
            }

            public Operator next() {
                return operators[pos++];
            }

            public void remove() {
                Assert.assertUnsupportedOperation() ;
            }
        };
        return new OperatorIterator(iterator);
    }

    private class OperatorIterator implements Iterator<Operator> {
        private Stack<Iterator<Operator>> stack = new Stack<>();

        private OperatorIterator(Iterator<Operator> it) {
            stack.push(it);
        }

        @Override
        public void remove() {
            Assert.assertUnsupportedOperation();
        }

        @Override
        public boolean hasNext() {
            if (stack.peek().hasNext()) {
                return true;
            }
            stack.pop();
            return !stack.isEmpty() && stack.peek().hasNext();
        }

        @Override
        public Operator next() {
            Operator next = stack.peek().next();
            if (next.asOperators().isNone()) {
                return next;
            } else {
                stack.push(next.asOperators().iterator());
                return stack.peek().next();
            }
        }
    }
}
