package com.github.soursop.matrix.operator;


public interface Either<L, R> {
    boolean isRight();
    boolean isLeft();
    L left();
    R right();
    Either<L, R> map(Function<R, R> function);

    abstract class AbstractEither<L, R> implements Either<L, R> {
        @Override
        public Either<L, R> map(Function<R, R> function)  {
            return isRight()? create(function.apply(right())) : this;
        }
        abstract Either<L, R> create(R right);
    }
}
