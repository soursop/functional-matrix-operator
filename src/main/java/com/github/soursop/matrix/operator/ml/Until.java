package com.github.soursop.matrix.operator.ml;


public class Until<T> {
    private T theta;

    public static <T> Until<T> of(T theta) {
        return new Until<>(theta);
    }

    private Until(T theta) {
        this.theta = theta;
    }

    public Repeat repeat(int size) {
        return new Repeat(size);
    }

    public class Repeat {
        private final int repeat;

        private Repeat(int repeat) {
            this.repeat = repeat;
        }

        public T by(Derivative<T> gradient) {
            for (int i = 0; i < repeat; i++) {
                theta = gradient.gradient(theta);
            }
            return theta;
        }
    }
}
