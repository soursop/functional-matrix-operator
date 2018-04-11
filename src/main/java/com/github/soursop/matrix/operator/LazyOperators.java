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
    public Operator minus() {
        return orgin.minus();
    }

    @Override
    public Operator divide() {
        return orgin.divide();
    }

    @Override
    public Operator pow(int pow) {
        return orgin.pow(pow);
    }

    @Override
    public Operator apply(Function function) {
        return orgin.apply(function);
    }

    @Override
    public Operators transpose() {
        return orgin.transpose();
    }

    @Override
    protected CharSequence asSimple(String prefix, int depth) {
        append(orgin.asSimple(prefix, depth));
        return getBuilder();
    }

    @Override
    protected CharSequence _asSimple(int depth) {
        return asSimple("", depth);
    }
}
