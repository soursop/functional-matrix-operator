package com.github.soursop.matrix.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinkedDoubleMatrix extends AbstractMatrix implements DoubleMatrix {
    private final int height;
    private final int width;
    private List<Matrix> matrixes;

    protected LinkedDoubleMatrix(Matrix ... matrices) {
        this(Arrays.asList(matrices));
    }

    protected LinkedDoubleMatrix(List<Matrix> matrixes) {
        this.matrixes = matrixes;
        int width = 0;
        int height = 0;
        for(Matrix matrix : matrixes) {
            width = width + matrix.width();
            height = Assert.assertSameHeightExceptZero(height, matrix.height());
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int width() {
        return width;
    }

    public LinkedDoubleMatrix add(Matrix matrix) {
        List<Matrix> matrices = new ArrayList<>(matrixes);
        matrices.add(matrix);
        return new LinkedDoubleMatrix(matrixes);
    }

    @Override
    public double valueOf(int height, int width) {
        int pos = 0;
        for (Matrix matrix : matrixes) {
            if (pos + matrix.width() > width) {
                return matrix.valueOf(height, width - pos);
            } else {
                pos = pos + matrix.width();
            }
        }
        throw new Assert.WrongMatrixIndexException("Could't found value of index %d:%d from %s", height, width, toString());
    }

    @Override
    public double valueOf(int idx) {
        int pos = 0;
        for (Matrix matrix : matrixes) {
            if (pos + matrix.size() > idx) {
                return matrix.valueOf(matrix.size() - pos);
            } else {
                pos = pos + matrix.size();
            }
        }
        throw new Assert.WrongMatrixIndexException("Could't found value of index %d from %s", idx, toString());
    }

    @Override
    public Matrix transpose() {
        return new LinkedDoubleTransposeMatrix(this);
    }

    @Override
    public double[] values() {
        double[] doubles = new double[size()];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = valueOf(i);
        }
        return doubles;
    }

    @Override
    public DoubleMatrix asDoubleMatrix() {
        return this;
    }

    private static class LinkedDoubleTransposeMatrix extends AbstractTranspose implements DoubleMatrix {
        private final LinkedDoubleMatrix origin;
        private LinkedDoubleTransposeMatrix(LinkedDoubleMatrix linkedDoubleMatrix) {
            origin = linkedDoubleMatrix;
        }

        @Override
        protected Matrix origin() {
            return origin;
        }

        @Override
        public double[] values() {
            double[] doubles = new double[size()];
            for (int i = 0; i < doubles.length; i++) {
                doubles[i] = valueOf(i);
            }
            return doubles;
        }

        @Override
        public LinkedDoubleMatrix transpose() {
            return origin;
        }

        @Override
        public DoubleMatrix asDoubleMatrix() {
            return this;
        }
    }
}
