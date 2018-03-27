package com.github.soursop.matrix.operator;

import java.util.*;

public class LinkedDoubleMatrix extends DoubleMatrix implements Matrix, Iterable<Double> {
    private final int height;
    private final int width;
    private List<DoubleMatrix> matrices;

    protected LinkedDoubleMatrix(DoubleMatrix ... matrices) {
        this(Arrays.asList(matrices));
    }

    protected LinkedDoubleMatrix(List<DoubleMatrix> matrices) {
        this.matrices = matrices;
        int width = 0;
        int height = 0;
        for(DoubleMatrix matrix : matrices) {
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

    @Override
    public double valueOf(int height, int width) {
        int pos = 0;
        for (DoubleMatrix matrix : matrices) {
            if (pos + matrix.width() > width) {
                return matrix.valueOf(height, width - pos);
            } else {
                pos = pos + matrix.width();
            }
        }
        throw new Assert.WrongMatrixIndexException("Could't found value of index height:%d width:%d from %s", height, width, asSimple(0));
    }

    @Override
    public double valueOf(int idx) {
        int pos = 0;
        int height = idx / width();
        int remain = idx % width();
        for (DoubleMatrix matrix : matrices) {
            if (pos + matrix.size() > remain) {
                return matrix.valueOf(height, remain);
            } else {
                pos = pos + matrix.size();
            }
        }
        throw new Assert.WrongMatrixIndexException("Could't found value of index:%d from %s", idx, toString());
    }

    @Override
    public DoubleMatrix transpose() {
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

    @Override
    public Iterator<Double> iterator() {
        return new LinkedIterator(matrices.iterator());
    }

    private class LinkedIterator implements Iterator<Double> {
        private final List<DoubleMatrix> list = new ArrayList<>();
        private final Map<Integer, Integer> limit = new HashMap();
        private int pos = 0;
        private int index = 0;
        private final Stack<Iterator<DoubleMatrix>> stack = new Stack<>();
        public LinkedIterator(Iterator<DoubleMatrix> iterator) {
            stack.push(iterator);
        }

        @Override
        public boolean hasNext() {
            return pos < size();
        }

        @Override
        public Double next() {
            if (!limit.containsKey(index)) {
                DoubleMatrix next = stack.peek().next();
                while (Iterable.class.isAssignableFrom(next.getClass())) {
                    stack.push(((Iterable<DoubleMatrix>) next).iterator());
                    next = stack.peek().next();
                }
                if (!stack.peek().hasNext()) {
                    stack.pop();
                }
                limit.put(index, index == 0? next.width() : limit.get(index - 1) + next.width());
                list.add(next);
            }
            int w = index == 0? pos % width() : (pos % width) - limit.get(index - 1);
            Double next = list.get(index).valueOf(pos / width(), w);
            pos = pos + 1;
            index = (pos % width()) < limit.get(index)? index : (index + 1) % width();
            return next;
        }
    }


    private static class LinkedDoubleTransposeMatrix extends DoubleTranspose implements Matrix {
        private final LinkedDoubleMatrix origin;
        private LinkedDoubleTransposeMatrix(LinkedDoubleMatrix origin) {
            this.origin = origin;
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
