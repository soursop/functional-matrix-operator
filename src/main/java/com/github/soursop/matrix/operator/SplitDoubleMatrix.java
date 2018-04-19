package com.github.soursop.matrix.operator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author soursop
 * @created 2018. 4. 9.
 */
public class SplitDoubleMatrix extends RecursiveTask<DoubleMatrix> {
    private final int size;
    private final DoubleMatrix origin;
    private final Product product;

    public SplitDoubleMatrix(DoubleMatrix origin, Product product, int size) {
        this.origin = origin;
        this.product = product;
        this.size = size;
    }

    @Override
    protected DoubleMatrix compute() {
        if (origin.height() > size) {
            return combine(ForkJoinTask.invokeAll(create()));
        } else {
            return invoke(origin);
        }
    }

    private DoubleMatrix combine(Collection<SplitDoubleMatrix> results) {
        DoubleMatrix[] result = new DoubleMatrix[results.size()];
        int i = 0;
        for (SplitDoubleMatrix split : results) {
            result[i++] = split.join();
        }
        return new Under(result).invoke();
    }

    private List<SplitDoubleMatrix> create() {
        List<DoubleMatrix> doubleMatrices = origin.splitBy(size);
        ArrayList<SplitDoubleMatrix> list = new ArrayList<>();
        for (DoubleMatrix doubleMatrix : doubleMatrices) {
            list.add(new SplitDoubleMatrix(doubleMatrix, product, size));
        }
        return list;
    }

    private DoubleMatrix invoke(DoubleMatrix matrix) {
        return product.invoke(matrix);
    }

}
