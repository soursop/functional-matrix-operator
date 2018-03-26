package com.github.soursop.matrix.operator;

class Assert {
    static class WrongMatrixSizeException extends IllegalArgumentException {
        protected WrongMatrixSizeException(String format, Object ... objs) {
            super(String.format(format, objs));
        }
    }
    static class WrongMatrixIndexException extends IllegalArgumentException {
        protected WrongMatrixIndexException(String format, Object ... objs) {
            super(String.format(format, objs));
        }
    }
    static int assertSameHeightExceptZero(int one, int another) {
        if (one != 0 && another != 0 && one != another) {
            throw new WrongMatrixSizeException("Illeagal Matrix height size %d != %d", one, another);
        }
        return Math.max(one, another);
    }
    static int assertHeight(int size, int width) {
        if (size % width != 0) {
            throw new WrongMatrixSizeException("Illeagal Matrix size %d / %d = %d", size, width, size % width);
        }
        return size / width;
    }
}
