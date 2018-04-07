package com.github.soursop.matrix.operator;

import java.util.ArrayList;
import java.util.List;

public class Functions {
    public static String asFormat(String format, Object ... objects) {
        return String.format(format, objects);
    }

    public static List<Double> asList(double ... doubles) {
        List<Double> list = new ArrayList<>();
        for (double v : doubles) {
            list.add(v);
        }
        return list;
    }
}
