package com.github.soursop.matrix.operator;

import java.util.Random;

public class DoubleRandomIterator extends AbstractDoubleMatrix {
    private final Random random;
    private final int height;
    private final int width;

    public DoubleRandomIterator(int height, int width) {
        this(height, width, new Random().nextInt());
    }

    public DoubleRandomIterator(int height, int width, int seed) {
        this.random = new Random(seed);
        this.height = height;
        this.width = width;
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
        Assert.assertIndexException(height, width, this);
        return random.nextDouble();
    }

}
