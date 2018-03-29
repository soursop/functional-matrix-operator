package com.github.soursop.matrix.operator;

public class NextDoubleMatrix extends DoubleMatrix {
    private final int height;
    private final int width;
    private DoubleMatrix[] matrices;

    protected NextDoubleMatrix(DoubleMatrix... matrices) {
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
        throw Assert.throwIndexException(height, width, this);
    }

    @Override
    public DoubleMatrix transpose() {
        return new DoubleMatrixTranspose<>(this);
    }
}
