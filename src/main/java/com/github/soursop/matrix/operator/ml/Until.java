package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;

public class Until {
    private DoubleMatrix theta;

    public static Until of(DoubleMatrix theta) {
        return new Until(theta);
    }

    private Until(DoubleMatrix theta) {
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

        public DoubleMatrix by(Derivative gradient) {
            for (int i = 0; i < repeat; i++) {
                theta = gradient.gradient(theta);
            }
            return theta;
        }
    }
}
