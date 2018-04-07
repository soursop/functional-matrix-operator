package com.github.soursop.matrix.operator.utils;

import com.github.soursop.matrix.operator.io.TextDoubleWritable;
import java.io.*;
import java.net.URL;

public class Utils {

    public static DataInput from(String resource) throws IOException {
        URL url = com.google.common.io.Resources.getResource(resource);
        return new DataInputStream(url.openStream());
    }

    public static double[] read(String resource) throws IOException {
        return read(from(resource));
    }

    public static double[] read(DataInput input) throws IOException {
        TextDoubleWritable writable = new TextDoubleWritable();
        while (writable.hasNext()) {
            writable.readFields(input);
        }
        return writable.getValues();
    }
}
