package com.github.soursop.matrix.operator;

import java.util.Arrays;

public class UnderDoubleMatrix extends AbstractDoubleMatrix implements DoubleMatrix {
    private final int height;
    private final int width;
    private DoubleMatrix[] matrices;

    public UnderDoubleMatrix(DoubleMatrix... matrices) {
        this.matrices = matrices;
        int width = 0;
        int height = 0;
        for(DoubleMatrix matrix : matrices) {
            height = height + matrix.height();
            width = Assert.assertSameWidthExceptZero(width, matrix.width());
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
            if (pos + matrix.height() > height) {
                return matrix.valueOf(height - pos, width);
            } else {
                pos = pos + matrix.height();
            }
        }
        throw Assert.throwIndexException(height, width, this);
    }

    @Override
    public DoubleMatrix transpose() {
        return new DoubleMatrixTranspose<>(this);
    }

    @Override
    public DoubleMatrix head() {
        return matrices.length == 0? None.DOUBLE_MATRIX : matrices[0];
    }

    @Override
    public DoubleMatrix tail() {
        return matrices.length == 1? None.DOUBLE_MATRIX : matrices.length == 2? matrices[1] : new UnderDoubleMatrix(Arrays.copyOfRange(matrices, 1, matrices.length));
    }
}
