package com.github.soursop.matrix.operator;


import java.util.Arrays;

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

    public static DoubleMatrix[] unfold(DoubleMatrix matrix, int[] pos) {
        double[] values = matrix.values();
        DoubleMatrix[] matrices = new DoubleMatrix[pos.length / 2];
        int from = 0;
        for (int i = 0; i < pos.length; i = i + 2) {
            int height = pos[i];
            int width = pos[i + 1];
            int size = height * width;
            matrices[i / 2] = new DenseDoubleMatrix(height, width, Arrays.copyOfRange(values, from, from + size));
            from += size;
        }
        return matrices;
    }

    @Override
    public int[] pos() {
        return pos;
    }
}