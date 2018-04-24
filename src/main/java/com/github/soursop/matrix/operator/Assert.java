package com.github.soursop.matrix.operator;

public class Assert {

    public static void assertElementSize(Object applier, DoubleMatrix one, DoubleMatrix other) {
        if (one.height() != other.height() || one.width() != one.width()) {
            throw new IllegalArgumentException(String.format("Could't match %s of %s and %s", Sign.sign(applier.getClass()), one.asSimple(0), other.asSimple(0)));
        }
    }

    public static void assertProductSize(Object applier, DoubleMatrix one, DoubleMatrix other) {
        if (one.width() != other.height()) {
            throw new IllegalArgumentException(String.format("Could't match %s of %s and %s", Sign.sign(applier.getClass()), one.asSimple(0), other.asSimple(0)));
        }
    }

    public static <T> T assertOnlyOne(T[] t) {
        if (t.length > 1 || t.length == 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("Could't support values of size:%d", t.length));
        }
        return t[0];
    }

    public static int assertSameHeightExceptZero(int one, int another) {
        if (one != 0 && another != 0 && one != another) {
            throw new IllegalArgumentException(String.format("Illegal matrix height size %d != %d", one, another));
        }
        return Math.max(one, another);
    }

    public static int assertSameWidthExceptZero(int one, int another) {
        if (one != 0 && another != 0 && one != another) {
            throw new IllegalArgumentException(String.format("Illegal matrix width size %d != %d", one, another));
        }
        return Math.max(one, another);
    }

    public static int assertHeight(int size, int width) {
        if (size % width != 0) {
            throw new IllegalArgumentException(String.format("Illegal matrix size %d / %d = %d", size, width, size % width));
        }
        return size / width;
    }

    public static void assertUnsupportedOperation() {
        throw new UnsupportedOperationException("Cannot remove an element of an array.");
    }

    public static UnsupportedOperationException throwUnsupportedOperation(String name, Class<?> clazz) {
        return new UnsupportedOperationException(String.format("Cannot suppprt %s from %s", name, clazz.getSimpleName()));
    }
}
