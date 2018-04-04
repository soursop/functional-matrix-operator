package com.github.soursop.matrix.operator;

import java.util.Iterator;
import java.util.Stack;

abstract class AbstractOperators extends AbstractOperator implements Operators {
    private final Operator[] operators;
    private final Applier applier;

    protected AbstractOperators(Applier applier, Operator... operators) {
        this.operators = operators;
        this.applier = applier;
    }

    @Override
    public Operator[] getOperators() {
        return operators;
    }

    @Override
    public DoubleMatrix invoke() {
        return invoke(None.DOUBLE_MATRIX);
    }

    private Operator apply(Operator one, Operator other) {
        boolean isOne = one.asDoubleMatrix().isSome();
        boolean isOther = other.asDoubleMatrix().isSome();
        if (isOne && isOther) {
            return applier.apply(one.asDoubleMatrix(), other.asDoubleMatrix());
        } else if (isOne) {
            return applier.apply(one.asDoubleMatrix(), other.asDoubleOperator());
        } else if (isOther) {
            return applier.apply(one.asDoubleOperator(), other.asDoubleMatrix());
        } else {
            return applier.apply(one.asDoubleOperator(), other.asDoubleOperator());
        }
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        Operator base = prev;
        for (Operator op : getOperators()) {
            base = op.asOperators().invoke(base);
            base = apply(base, op);
        }
        return base.asDoubleMatrix();
    }

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
    public Operators minus() {
        return MinusInvoker.of(this);
    }

    @Override
    public Operators divide() {
        return DivideInvoker.of(this);
    }

    @Override
    public CharSequence asSimple(int depth) {
        return asSimple(applier.symbol(), depth);
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
