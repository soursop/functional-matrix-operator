package com.github.soursop.matrix.operator;

import java.util.Iterator;
import java.util.Stack;

abstract class AbstractOperators extends AbstractOperator implements Operators {

    @Override
    public Operators asOperators() {
        return this;
    }

    @Override
    public DoubleMatrix asDoubleMatrix() {
        return None.DOUBLE_MATRIX;
    }

    @Override
    public DoubleOperator asDoubleOperator() {
        return None.DOUBLE_OPERATOR;
    }

    @Override
    public CharSequence asSimple(int depth) {
        return asSimple("", depth);
    }

    protected CharSequence asSimple(String prefix, int depth) {
        initBuilder(depth);
        Operator[] operators = getOperators();
        withPadding();
        append(prefix);
        if (operators.length > 1) {
            append("(\n");
        }
        for (int i = 0; i < operators.length; i++) {
            append(operators[i].asSimple(depth + 1));
            if (i + 1 < operators.length) {
                append("\n");
                withPadding(Sign.sign(getClass()));
                append("\n");
            }
        }
        if (operators.length > 1) {
            append("\n");
            withPadding(")");
        }
        return getBuilder();
    }

    @Override
    public Iterator<Operator> iterator() {
        Iterator<Operator> iterator = new Iterator<Operator>() {
            private int pos = 0;

            public boolean hasNext() {
                return getOperators().length > pos;
            }

            public Operator next() {
                return getOperators()[pos++];
            }

            public void remove() {
                Assert.assertUnsupportedOperation();
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
