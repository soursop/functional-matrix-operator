package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractMinimizer implements Minimizer {


    private List<IterationListener> listenerList = new ArrayList<>();

    /**
     * Add a callback listener that triggers after a iteration.
     *
     * @param lst the iteration completion listener.
     */
    public void addIterationCompletionCallback(IterationListener lst) {
        listenerList.add(lst);
    }

    /**
     * Callback method that should be called from an explicit subclass once an
     * iteration was finished.
     *
     * @param iteration the number of the current iteration.
     * @param cost the cost at the current iteration.
     * @param currentWeights the current optimal weights.
     */
    protected final void onFinished(int iteration, double cost,
                                             DoubleMatrix currentWeights) {
        for (IterationListener list : listenerList) {
            list.onFinished(iteration, cost, currentWeights);
        }
    }
}
