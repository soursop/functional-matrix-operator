package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Until {
    private static final Log LOG = LogFactory.getLog(Until.class);
    private final DoubleMatrix theta;

    public Until(DoubleMatrix theta) {
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

        public Cost by(Gradient gradient) {
            Cost cost = new Cost(0, theta);
            for (int i = 0; i < repeat; i++) {
                cost = gradient.gradient(cost.theta());
                if (LOG.isInfoEnabled()) {
                    LOG.info("Iteration " + i + " | Cost: " + cost.cost());
                }
            }
            return cost;
        }
    }
}
