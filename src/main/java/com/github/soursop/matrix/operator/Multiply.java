package com.github.soursop.matrix.operator;

import java.util.List;

public class Multiply extends AbstractOperators {

    protected Multiply(List<Operator> operators) {
        super(operators);
    }

//    private Matrix apply(List<Matrix> prev) {
//        double[] values = null;
//        for (Matrix matrix : prev) {
//            if (values == null) {
//                values = matrix.values();
//            } else {
//                matrix.apply(values)
//            }
//        }
//        return prev;
//    }

    @Override
    public <T extends Operator> T apply() {
        return null;
    }

    @Override
    public <T extends Operator> T apply(T prev) {
        return apply(prev);
    }
}
