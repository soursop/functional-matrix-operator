package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.AbstractDoubleMatrix;

import java.util.Random;

public class DoubleEpsilonIterator extends AbstractDoubleMatrix {
    private final Random random;
    private final int height;
    private final int width;
    private final double epsilon;

    public DoubleEpsilonIterator(int height, int width, double epsilon) {
        this(height, width, new Random().nextInt(), epsilon);
    }

    public DoubleEpsilonIterator(int height, int width, int seed, double epsilon) {
        this.random = new Random(seed);
        this.height = height;
        this.width = width;
        this.epsilon = epsilon;
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
        return random.nextDouble() * 2 * epsilon - epsilon;
    }

    @Override
    public double valueOf(int idx) {
        return random.nextDouble() * 2 * epsilon - epsilon;
    }

}
