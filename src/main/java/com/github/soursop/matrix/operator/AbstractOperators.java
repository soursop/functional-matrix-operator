package com.github.soursop.matrix.operator;

import java.util.Iterator;
import java.util.Stack;

abstract class AbstractOperators extends AbstractOperator implements Operators, Transposable<Operators> {
    private final Operator[] operators;
    private final With with;

    protected AbstractOperators(With with, Operator... operators) {
        this.operators = operators;
        this.with = with;
    }

    private AbstractOperators(AbstractOperators origin) {
        this.operators = origin.operators;
        this.with = origin.with;
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
            return with.apply(one.asDoubleMatrix(), other.asDoubleMatrix());
        } else if (isOne) {
            return with.apply(one.asDoubleMatrix(), other.asDoubleOperator());
        } else if (isOther) {
            return with.apply(one.asDoubleOperator(), other.asDoubleMatrix());
        } else {
            return with.apply(one.asDoubleOperator(), other.asDoubleOperator());
        }
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        Operator base = prev;
        for (Operator op : operators) {
            Operator next = op.asOperators().isSome()? op.asOperators().invoke() : op;
            base = apply(base, next);
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
        return new MinusOperators(this);
    }

    @Override
    public Operators divide() {
        return new DivideOperators(this);
    }

    @Override
    public Operators pow(final int pow) {
        return new LazyOperators("pow", new Function() {
            @Override
            public double apply(double v) {
                return Math.pow(v, pow);
            }
        }, this);
    }

    @Override
    public Operators transpose() {
        return new TransposeOperators(this);
    }

    @Override
    public Operators apply(Function function) {
        return new LazyOperators(function, this);
    }

    @Override
    protected CharSequence _asSimple(int depth) {
        return asSimple(with.symbol(), depth);
    }

    protected CharSequence asSimple(String sign, int depth) {
        Operator[] operators = getOperators();
        withPadding();
        if (operators.length > 1) {
            append("(\n");
        }
        for (int i = 0; i < operators.length; i++) {
            append(operators[i].asSimple(depth + 1));
            if (i + 1 < operators.length) {
                append("\n");
                withPadding(sign);
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

    private class LazyOperators extends AbstractOperators {
        private final String sign;
        private final Function function;

        private LazyOperators(Function function, AbstractOperators origin) {
            this("func", function, origin);
        }

        private LazyOperators(String sign, Function function, AbstractOperators origin) {
            super(origin);
            this.sign = sign;
            this.function = function;
        }

        @Override
        public DoubleMatrix invoke(Operator prev) {
            DoubleMatrix invoke = super.invoke(prev);
            return new LazyDoubleMatrix<>(sign, function, invoke);
        }

        @Override
        public CharSequence asSimple(int depth) {
            return sign + super.asSimple(depth);
        }
    }

    private class TransposeOperators extends AbstractOperators {
        private final Operators origin;
        private TransposeOperators(AbstractOperators origin) {
            super(origin);
            this.origin = origin;
        }

        @Override
        public DoubleMatrix invoke(Operator prev) {
            DoubleMatrix invoke = super.invoke(prev);
            return new DoubleMatrixTranspose<>(invoke);
        }

        @Override
        public Operators transpose() {
            return origin;
        }

        @Override
        public CharSequence asSimple(int depth) {
            return super.asSimple(depth) + "'";
        }
    }

    private class DivideOperators extends AbstractOperators {
        private final Operators origin;
        private DivideOperators(AbstractOperators origin) {
            super(origin);
            this.origin = origin;
        }

        @Override
        public DoubleMatrix invoke(Operator prev) {
            DoubleMatrix invoke = super.invoke(prev);
            return new DivideDoubleMatrix<>(invoke);
        }

        @Override
        public Operators divide() {
            return origin;
        }

        @Override
        public CharSequence asSimple(int depth) {
            return "1/" + super.asSimple(depth);
        }
    }

    private class MinusOperators extends AbstractOperators {
        private final Operators origin;
        private MinusOperators(AbstractOperators origin) {
            super(origin);
            this.origin = origin;
        }

        @Override
        public DoubleMatrix invoke(Operator prev) {
            DoubleMatrix invoke = super.invoke(prev);
            return new MinusDoubleMatrix<>(invoke);
        }

        @Override
        public Operators minus() {
            return origin;
        }

        @Override
        public CharSequence asSimple(int depth) {
            return "-" + super.asSimple(depth);
        }
    }

}
