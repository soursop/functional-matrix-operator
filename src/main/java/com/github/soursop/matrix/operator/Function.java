package com.github.soursop.matrix.operator;

import org.apache.commons.math3.util.FastMath;

public interface Function {
    double apply(double v);

    Function LOG = new Function() {
        @Override
        public double apply(double input) {
            if (Double.isNaN(input) || Double.isInfinite(input)) {
                return 0d;
            } else if (input <= 0d || input <= -0d) {
                // assume a quite low value of log(1e-5) ~= -11.51
                return -10d;
            } else {
                return FastMath.log(input);
            }
        }
    }
    ;
}
