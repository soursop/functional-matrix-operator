package com.github.soursop.matrix.operator;

import static com.github.soursop.matrix.operator.Utils.asFormat;

class Assert {
    static IllegalArgumentException throwIndexException(int height, int width, DoubleMatrix matrix) {
        return new IllegalArgumentException(asFormat("Could't found value of index height:%d width:%d from %s", height, width, matrix.asSimple(0)));
    }

    static int assertSameHeightExceptZero(int one, int another) {
        if (one != 0 && another != 0 && one != another) {
            throw new IllegalArgumentException(asFormat("Illegal matrix height size %d != %d", one, another));
        }
        return Math.max(one, another);
    }

    static int assertHeight(int size, int width) {
        if (size % width != 0) {
            throw new IllegalArgumentException(asFormat("Illegal matrix size %d / %d = %d", size, width, size % width));
        }
        return size / width;
    }
    static void assertUnsupportedOperation() {
        throw new UnsupportedOperationException("Cannot remove an element of an array.");
    }
}
