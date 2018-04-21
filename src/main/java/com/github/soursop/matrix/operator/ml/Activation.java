package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.Function;
import org.apache.commons.math3.util.FastMath;

public interface Activation {
    Function active();
    Function gradient();

    Activation SIGMOID = new Activation() {
        private static final double CLIP = 30d;
        Function ACTIVE = new Function() {
            @Override
            public double apply(double input) {
                return FastMath.min(CLIP,
                        FastMath.max(-CLIP, 1.0 / (1.0 + FastMath.exp(-input))));
            }
        }
        ;
        Function GRADIENT = new Function() {
            @Override
            public double apply(double input) {
                return ACTIVE.apply(input) * (1d - ACTIVE.apply(input));
            }
        }
        ;
        @Override
        public Function active() {
            return ACTIVE;
        }

        @Override
        public Function gradient() {
            return GRADIENT;
        }
    }
    ;

}
