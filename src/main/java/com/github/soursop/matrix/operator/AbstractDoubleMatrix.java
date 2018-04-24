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

    protected static int indexOf(int h, int w, int width) {
        return h * width + w;
    }

    protected static int indexOf(int h, int width) {
        return h * width;
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
    public DoubleMatrix copy() {
        return new DenseDoubleMatrix(height(), width(), values());
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
        for (int i = 0; i < doubles.length; i++) {
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
        int repeat = height() / size + (height() % size == 0? 0 : 1);
        List<DoubleMatrix> list = new ArrayList<>();
        double[] values = values();
        int width = width();
        for (int i = 0; i < repeat; i++) {
            int next = (i + 1) * size;
            list.add(SplitDoubleMatrix.of(i * size, next >= height()? height() : next, width, values));
        }
        return list;
    }

    private static class SplitDoubleMatrix extends DenseDoubleMatrix {
        private SplitDoubleMatrix(int height, int width, double[] values) {
            super(height, width, values);
        }

        static SplitDoubleMatrix of(int from, int to, int width, double[] values) {
            return new SplitDoubleMatrix(to - from, width
                    , Arrays.copyOfRange(values, indexOf(from, width), indexOf(to, width)));
        }
    }

    private static class VectorDoubleMatrix extends DenseDoubleMatrix {
        private VectorDoubleMatrix(int height, int width, double[] values) {
            super(height, width, values);
        }

        static double[] combine(int from, int to, DoubleMatrix parent) {
            int subtract = to - from;
            int width = parent.width();
            int height = parent.height();
            double[] values = new double[height * subtract];
            double[] origin = parent.values();
            for(int h = 0; h < height; h++) {
                System.arraycopy(origin, indexOf(h, from, width), values, indexOf(h, subtract), subtract);
            }
            return values;
        }

        static VectorDoubleMatrix of(int from, int to, DoubleMatrix parent) {
            return new VectorDoubleMatrix(parent.height(), to - from, combine(from, to, parent));
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
