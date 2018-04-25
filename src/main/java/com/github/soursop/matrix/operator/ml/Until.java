package com.github.soursop.matrix.operator.ml;

public class Until<T> {
    private T theta;

    public Until(T theta) {
        this.theta = theta;
    }

    public Repeat<T> repeat(int size) {
        return new Repeat<>(theta, size);
    }

    public static class Repeat<T> {
        private final int repeat;
        private T theta;

        private Repeat(T theta, int repeat) {
            this.repeat = repeat;
            this.theta = theta;
        }

        public Assessed<T> by(Derivative<T> gradient) {
            Assessed<T> assessed = gradient.init(theta);
            for (int i = 0; i < repeat; i++) {
                assessed = gradient.gradient(assessed);
            }
            return assessed;
        }
    }
}
