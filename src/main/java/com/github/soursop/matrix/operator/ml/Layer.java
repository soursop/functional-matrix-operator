package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleIterator;
import com.github.soursop.matrix.operator.DoubleMatrix;
import com.github.soursop.matrix.operator.NextDoubleMatrix;

/**
 * @author soursop
 * @created 2018. 4. 19.
 */
public class Layer extends NextDoubleMatrix {
    public Layer(DoubleMatrix origin) {
        super(new DoubleIterator(1d, origin.height(), 1), origin);
    }
}
