package com.github.soursop.matrix.operator;

abstract class WithOperators extends AbstractOperators {
    private final Operator[] operators;
    private final With with;

    protected WithOperators(With with, Operator... operators) {
        this.operators = operators;
        this.with = with;
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
            Operator next = op.asOperators().isSome() ? op.asOperators().invoke() : op;
            base = apply(base, next);
        }
        return base.asDoubleMatrix();
    }

    @Override
    public SumOperator sum() {
        return SumOperator.of(invoke());
    }

    @Override
    public AvgOperator avg() {
        return AvgOperator.of(invoke());
    }

    @Override
    public StdOperator std() {
        return StdOperator.of(invoke());
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
    public Operators pow(int pow) {
        return new PowOperators(this, pow);
    }

    @Override
    public Operators apply(Function function) {
        return new FunctionOperators(function, this);
    }

    @Override
    public Operators transpose() {
        return new TransposeOperators(this);
    }

}
