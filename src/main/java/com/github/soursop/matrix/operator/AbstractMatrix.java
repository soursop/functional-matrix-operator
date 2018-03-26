package com.github.soursop.matrix.operator;

abstract class AbstractMatrix extends AbstractOperator implements Matrix {

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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int h = 0; h < height(); h++) {
            for (int w = 0; w < width(); w++) {
                builder.append(valueOf(h, w));
                if (w + 1 < width()) {
                    builder.append(",");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public int size() {
        return height() * width();
    }
}
