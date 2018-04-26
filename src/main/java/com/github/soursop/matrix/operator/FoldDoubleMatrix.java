package com.github.soursop.matrix.operator;


/**
 * @author soursop
 * @created 2018. 4. 26.
 */
public class FoldDoubleMatrix extends DenseDoubleMatrix {
    private final int[] pos;

    public static FoldDoubleMatrix of(DoubleMatrix... matrices) {
        int size = 0;
        int[] pos = new int[matrices.length * 2];
        for(int i = 0; i < matrices.length; i++) {
            pos[i * 2] = matrices[i].height();
            pos[i * 2 + 1] = matrices[i].width();
            size += matrices[i].size();
        }
        double[] doubles = new double[size];
        int from = 0;
        for (DoubleMatrix matrix : matrices) {
            System.arraycopy(matrix.values(), 0, doubles, from, matrix.size());
            from += matrix.size();
        }

        return new FoldDoubleMatrix(doubles.length, 1, doubles, pos);
    }

    protected FoldDoubleMatrix(int height, int width, double[] values, int[] pos) {
        super(height, width, values);
        this.pos = pos;
    }

    @Override
    public int[] pos() {
        return pos;
    }
}