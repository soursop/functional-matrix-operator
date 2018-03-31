package com.github.soursop.matrix.operator;

import java.util.Iterator;

abstract class DoubleMatrix extends AbstractOperator implements LinkedMatrix<DoubleMatrix>, Iterable<Double> {
    static final DoubleMatrix NONE = new DenseDoubleMatrix(0, 0, new double[0]);

    @Override
    public CharSequence asSimple(int depth) {
        return new StringBuilder().append(height()).append(":").append(width());
    }

    @Override
    public DoubleOperator asDoubleOperator() {
        return DoubleOperator.NONE;
    }

    @Override
    public Operators asOperators() {
        return None;
    }

    @Override
    public DoubleMatrix asDoubleMatrix() {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int h = 0; h < height(); h++) {
            for (int w = 0; w < width(); w++) {
                builder.append(valueOf(h, w));
                if (w + 1 < width()) {
                    builder.append(",");
                }
            }
            if (h + 1 < height()) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public int size() {
        return height() * width();
    }

    @Override
    public double valueOf(int idx) {
        return valueOf(idx / width(), idx % width());
    }

    @Override
    public Iterator<Double> iterator() {
        return new DoubleMatrixIterator(this);
    }

    public double[] values() {
        double[] doubles = new double[size()];
        for (int i = 0; i < size(); i++) {
            doubles[i] = valueOf(i);
        }
        return doubles;
    }

    private static class DoubleMatrixIterator implements Iterator<Double> {
        private int idx = 0;
        private final DoubleMatrix parent;
        private DoubleMatrixIterator(DoubleMatrix parent) {
            this.parent = parent;
        }

        @Override
        public boolean hasNext() {
            return idx < parent.size();
        }

        @Override
        public Double next() {
            Double next = parent.valueOf(idx / parent.width(), idx % parent.width());
            idx++;
            return next;
        }

        @Override
        public void remove() {
            Assert.assertUnsupportedOperation();
        }
    }
}
