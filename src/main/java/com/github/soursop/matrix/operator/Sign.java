package com.github.soursop.matrix.operator;

class Sign {
    interface Plus {
        Calculation with = new Calculation() {
            @Override
            double apply(double v1, double v2) {
                return v1 + v2;
            }

            @Override
            protected DoubleMatrix some(DoubleMatrix one, DoubleMatrix other) {
                return elementWise(this, one, other);
            }

            @Override
            protected DoubleMatrix none(DoubleMatrix none, DoubleMatrix some) {
                return some;
            }

            @Override
            protected DoubleOperator some(DoubleOperator one, DoubleOperator other) {
                return new DoubleOperator(apply(one.getValue(), other.getValue()));
            }

            @Override
            protected DoubleOperator none(DoubleOperator none, DoubleOperator some) {
                return some;
            }
        };
    }

    interface Minus {
        Function function = new Function() {
            @Override
            public double apply(double v) {
                return -v;
            }
        };
    }

    interface Divide {
        Function function = new Function() {
            @Override
            public double apply(double v) {
                return 1 / v;
            }
        };
    }

    interface Product {
        Calculation with = new Calculation() {
            @Override
            double apply(double v1, double v2) {
                return v1 * v2;
            }

            @Override
            protected DoubleMatrix some(DoubleMatrix one, DoubleMatrix other) {
                return innerProduct(this, one, other);
            }

            @Override
            protected DoubleMatrix none(DoubleMatrix none, DoubleMatrix some) {
                return some;
            }

            @Override
            protected DoubleOperator some(DoubleOperator one, DoubleOperator other) {
                return new DoubleOperator(apply(one.getValue(), other.getValue()));
            }

            @Override
            protected DoubleOperator none(DoubleOperator none, DoubleOperator some) {
                return some;
            }
        };
    }

    interface Multiply {
        Calculation with = new Calculation() {
            @Override
            double apply(double v1, double v2) {
                return v1 * v2;
            }

            @Override
            protected DoubleMatrix some(DoubleMatrix one, DoubleMatrix other) {
                return elementWise(this, one, other);
            }

            @Override
            protected DoubleMatrix none(DoubleMatrix none, DoubleMatrix some) {
                return some;
            }

            @Override
            protected DoubleOperator some(DoubleOperator one, DoubleOperator other) {
                return new DoubleOperator(apply(one.getValue(), other.getValue()));
            }

            @Override
            protected DoubleOperator none(DoubleOperator none, DoubleOperator some) {
                return some;
            }
        };
    }

    interface Pow {
        interface PowFunction extends Pow, Function {
        }
    }

    interface Next {
        Conjunction with = new Conjunction() {
            @Override
            protected DoubleMatrix some(DoubleMatrix one, DoubleMatrix other) {
                return new NextDoubleMatrix(one, other);
            }

            @Override
            protected DoubleMatrix asIterator(DoubleOperator one, DoubleMatrix other) {
                return one.toIterator(other.height(), 1);
            }
        };
    }

    interface Under {
        Conjunction with = new Conjunction() {
            @Override
            protected DoubleMatrix some(DoubleMatrix one, DoubleMatrix other) {
                return new UnderDoubleMatrix(one, other);
            }

            @Override
            protected DoubleMatrix asIterator(DoubleOperator one, DoubleMatrix other) {
                return one.toIterator(1, other.width());
            }
        };
    }

    interface Transpose {

    }

    interface Sum {

    }

    interface NextSum extends Sum {

    }

    interface UnderSum extends Sum {

    }

    public static String sign(Class<?> clazz) {
        if (Plus.class.isAssignableFrom(clazz)) {
            return "+";
        } else if (Minus.class.isAssignableFrom(clazz)) {
            return "-";
        } else if (Divide.class.isAssignableFrom(clazz)) {
            return "1/";
        } else if (Product.class.isAssignableFrom(clazz)) {
            return "*";
        } else if (Multiply.class.isAssignableFrom(clazz)) {
            return ".*";
        } else if (Next.class.isAssignableFrom(clazz)) {
            return "::";
        } else if (Under.class.isAssignableFrom(clazz)) {
            return ";";
        } else if (Pow.class.isAssignableFrom(clazz)) {
            return "pow";
        } else if (Transpose.class.isAssignableFrom(clazz)) {
            return "'";
        } else if (Sum.class.isAssignableFrom(clazz)) {
            if (NextSum.class.isAssignableFrom(clazz)) {
                return "▶sum";
            } else if (UnderSum.class.isAssignableFrom(clazz)) {
                return "▼sum";
            } else {
                return "sum";
            }
        }
        return "";
    }
}
