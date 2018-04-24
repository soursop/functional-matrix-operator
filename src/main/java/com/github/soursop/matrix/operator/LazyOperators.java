package com.github.soursop.matrix.operator;

abstract class LazyOperators extends AbstractOperators {
    private final AbstractOperators orgin;

    protected LazyOperators(AbstractOperators orgin) {
        this.orgin = orgin;
    }

    @Override
    public Operator[] getOperators() {
        return orgin.getOperators();
    }

    @Override
    public DoubleMatrix invoke() {
        return orgin.invoke();
    }

    @Override
    public DoubleMatrix invoke(Operator prev) {
        return orgin.invoke(prev);
    }

    @Override
    public Operators minus() {
        return orgin.minus();
    }

    @Override
    public Operators divide() {
        return orgin.divide();
    }

    @Override
    public Operators pow(int pow) {
        return orgin.pow(pow);
    }

    @Override
    public Operators apply(Function function) {
        return orgin.apply(function);
    }

    @Override
    public Operators transpose() {
        return orgin.transpose();
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
    protected CharSequence asSimple(String prefix, int depth) {
        return orgin.asSimple(prefix, depth);
    }
}
