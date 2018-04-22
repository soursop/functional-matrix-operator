package com.github.soursop.matrix.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractDoubleMatrix extends AbstractOperator implements DoubleMatrix, Iterable<Double> {

    @Override
    public CharSequence asSimple(int depth) {
        initBuilder(depth);
        withPadding();
        append(Sign.sign(getClass()));
        append(height());
        append(":");
        append(width());
        return getBuilder();
    }

    @Override
    public DoubleOperator asDoubleOperator() {
        return None.DOUBLE_OPERATOR;
    }

    @Override
    public Operators asOperators() {
        return None.OPERATORS;
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
    public double[] row(int height) {
        double[] doubles = new double[width()];
        for (int w = 0; w < width(); w++) {
            doubles[w] = valueOf(height, w);
        }
        return doubles;
    }

    @Override
    public DoubleMatrix transpose() {
        return DoubleMatrixTranspose.of(this);
    }

    @Override
    public SumOperator sum() {
        return SumOperator.of(this);
    }

    @Override
    public AvgOperator avg() {
        return AvgOperator.of(this);
    }

    @Override
    public StdOperator std() {
        return StdOperator.of(this);
    }

    @Override
    public DoubleMatrix minus() {
        return new MinusDoubleMatrix<>(this);
    }

    @Override
    public DoubleMatrix divide() {
        return new DivideDoubleMatrix<>(this);
    }

    @Override
    public DoubleMatrix pow(final int pow) {
        return apply(new Sign.Pow.PowFunction() {
            @Override
            public double apply(double v) {
                return Math.pow(v, pow);
            }
        });
    }

    @Override
    public DoubleMatrix apply(Function function) {
        return new LazyDoubleMatrix<>(function, this);
    }

    @Override
    public int size() {
        return height() * width();
    }

    @Override
    public Iterator<Double> iterator() {
        return new DoubleMatrixIterator(this);
    }

    @Override
    public double[] values() {
        double[] doubles = new double[size()];
        for (int i = 0; i < size(); i++) {
            doubles[i] = valueOf(i);
        }
        return doubles;
    }

    @Override
    public DoubleMatrix head() {
        if (width() < 1) {
            return None.DOUBLE_MATRIX;
        }
        return VectorDoubleMatrix.of(0, 1, this);
    }

    @Override
    public DoubleMatrix tail() {
        if (width() < 2) {
            return None.DOUBLE_MATRIX;
        }
        return VectorDoubleMatrix.of(1, width(), this);
    }

    @Override
    public DoubleMatrix last() {
        if (width() < 1) {
            return None.DOUBLE_MATRIX;
        }
        return VectorDoubleMatrix.of(width() - 1, width(), this);
    }

    @Override
    public DoubleMatrix init() {
        if (width() < 2) {
            return None.DOUBLE_MATRIX;
        }
        return VectorDoubleMatrix.of(0, width() - 1, this);
    }

    @Override
    public List<DoubleMatrix> splitBy(int size) {
        int repeat = height() / size;
        List<DoubleMatrix> list = new ArrayList<>();
        for (int i = 0; i < repeat; i++) {
            int next = (i + 1) * size;
            list.add(SplitDoubleMatrix.of(i * size, next >= height()? height() : next, this));
        }
        return list;
    }

    private static class SplitDoubleMatrix extends DenseDoubleMatrix {
        private SplitDoubleMatrix(int width, double[] values) {
            super(width, values);
        }

        static SplitDoubleMatrix of(int from, int to, DoubleMatrix parent) {
            return new SplitDoubleMatrix(parent.width(), Arrays.copyOfRange(parent.values(), parent.width() * from, parent.width() * to));
        }
    }

    private static class VectorDoubleMatrix extends DenseDoubleMatrix {
        private VectorDoubleMatrix(int width, double[] values) {
            super(width, values);
        }

        static double[] combine(int from, int to, DoubleMatrix parent) {
            int width = to - from;
            double[] values = new double[parent.height() * width];
            for(int h = 0; h < parent.height(); h++) {
                System.arraycopy(parent.row(h), from, values, h * width, width);
            }
            return values;
        }

        static VectorDoubleMatrix of(int from, int to, DoubleMatrix parent) {
            return new VectorDoubleMatrix(to - from, combine(from, to, parent));
        }
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
