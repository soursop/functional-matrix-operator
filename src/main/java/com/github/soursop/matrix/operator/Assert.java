package com.github.soursop.matrix.operator;

import static com.github.soursop.matrix.operator.Utils.asFormat;

class Assert {
    static IllegalArgumentException throwIndexException(int height, int width, DoubleMatrix matrix) {
        return new IllegalArgumentException(asFormat("Could't found value of index height:%d width:%d from %s", height, width, matrix.asSimple(0)));
    }

    static void assertIndexException(int height, int width, DoubleMatrix matrix) {
        if (height >= matrix.height() || width >= matrix.width()) {
            throw throwIndexException(height, width, matrix);
        }
    }

    static void assertOutOfIndex(int idx, int size) {
        if (size <= idx) {
            throw new ArrayIndexOutOfBoundsException(asFormat("Could't found value of index:%d from size:%d", idx, size));
        }
    }

    static int assertSameHeightExceptZero(int one, int another) {
        if (one != 0 && another != 0 && one != another) {
            throw new IllegalArgumentException(asFormat("Illegal matrix height size %d != %d", one, another));
        }
        return Math.max(one, another);
    }

    static int assertSameWidthExceptZero(int one, int another) {
        if (one != 0 && another != 0 && one != another) {
            throw new IllegalArgumentException(asFormat("Illegal matrix width size %d != %d", one, another));
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

    static UnsupportedOperationException throwUnsupportedOperation(String name, Class<?> clazz) {
        return new UnsupportedOperationException(asFormat("Cannot suppprt %s from %s", name, clazz.getSimpleName()));
    }
}
