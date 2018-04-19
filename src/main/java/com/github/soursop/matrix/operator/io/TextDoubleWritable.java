package com.github.soursop.matrix.operator.io;

import com.google.common.base.Splitter;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author soursop
 * @created 2018. 4. 6.
 */
public class TextDoubleWritable implements Writable {
    private final DoubleBuffer buffer = new DoubleBuffer();
    private double[] array;
    private boolean hasNext = true;

    public void setValues(double[] array) {
        this.array = array;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        for (int i = 0; i < array.length; i++) {
            out.writeBytes(String.valueOf(array[i]));
            if (i + 1 < array.length) {
                out.writeBytes(",");
            }
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        String line = in.readLine();
        if (line == null) {
            hasNext = false;
            return;
        }
        Iterable<String> iterator = Splitter.on(",").split(line);
        for(String s : iterator) {
            buffer.add(Double.parseDouble(s));
        }
    }

    public boolean hasNext() {
        return hasNext;
    }

    public double[] getValues() {
        return array == null? buffer.values() : array;
    }

    public static class DoubleBuffer {
        private static int BUFFER = 100_000;
        private List<double[]> list = new ArrayList<>();
        private int size = 0;
        private int idx = 0;
        private double[] buffer = new double[BUFFER];

        public void add(double d) {
            if (idx == BUFFER) {
                idx = 0;
                size = size + BUFFER;
                list.add(buffer);
                buffer = new double[BUFFER];
            }
            buffer[idx++] = d;
        }

        public double[] values() {
            int total  = list.size() * BUFFER + idx;
            double[] out = new double[total];
            for (int i = 0; i < list.size(); i++) {
                double[] values = list.get(i);
                System.arraycopy(values, 0, out, i * BUFFER, values.length);
            }
            System.arraycopy(buffer, 0, out, list.size() * BUFFER, idx);
            return out;
        }
    }
}
