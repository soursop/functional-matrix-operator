package com.github.soursop.matrix.operator;

class Assert {
    static IllegalArgumentException throwIndexException(int height, int width, DoubleMatrix matrix) {
        return new IllegalArgumentException(String.format("Could't found value of index height:%d width:%d from %s", height, width, matrix.asSimple(0)));
    }

    static void assertElementSize(Applier applier, DoubleMatrix one, DoubleMatrix other) {
        if (one.height() != other.height() || one.width() != one.width()) {
            throw new IllegalArgumentException(String.format("Could't match %s of %s and %s", applier.symbol(), one.asSimple(0), other.asSimple(0)));
        }
    }

    static void assertProductSize(Applier applier, DoubleMatrix one, DoubleMatrix other) {
        if (one.width() != other.height()) {
            throw new IllegalArgumentException(String.format("Could't match %s of %s and %s", applier.symbol(), one.asSimple(0), other.asSimple(0)));
        }
    }

    static void assertIndexException(int height, int width, DoubleMatrix matrix) {
        if (height >= matrix.height() || width >= matrix.width()) {
            throw throwIndexException(height, width, matrix);
        }
    }

    static void assertOutOfIndex(int idx, int size) {
        if (size <= idx) {
            throw new ArrayIndexOutOfBoundsException(String.format("Could't found value of index:%d from size:%d", idx, size));
        }
    }

    static int assertSameHeightExceptZero(int one, int another) {
        if (one != 0 && another != 0 && one != another) {
            throw new IllegalArgumentException(String.format("Illegal matrix height size %d != %d", one, another));
        }
        return Math.max(one, another);
    }

    static int assertSameWidthExceptZero(int one, int another) {
        if (one != 0 && another != 0 && one != another) {
            throw new IllegalArgumentException(String.format("Illegal matrix width size %d != %d", one, another));
        }
        return Math.max(one, another);
    }

    static int assertHeight(int size, int width) {
        if (size % width != 0) {
            throw new IllegalArgumentException(String.format("Illegal matrix size %d / %d = %d", size, width, size % width));
        }
        return size / width;
    }

    static void assertUnsupportedOperation() {
        throw new UnsupportedOperationException("Cannot remove an element of an array.");
    }

    static UnsupportedOperationException throwUnsupportedOperation(String name, Class<?> clazz) {
        return new UnsupportedOperationException(String.format("Cannot suppprt %s from %s", name, clazz.getSimpleName()));
    }
}
