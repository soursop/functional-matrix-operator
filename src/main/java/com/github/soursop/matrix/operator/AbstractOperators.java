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
