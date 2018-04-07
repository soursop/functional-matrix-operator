package com.github.soursop.matrix.operator.io;

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
    private List<Double> list = new ArrayList<>();
    private double[] array;
    private boolean hasNext = true;

    public void setValues(double[] array) {
        this.array = array;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        int i = 0;
        for (double d : array) {
            out.writeBytes(String.valueOf(d));
            if (++i < array.length) {
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
        String[] split = line.split(",");
        for (String s : split) {
            list.add(Double.valueOf(s));
        }
    }

    public boolean hasNext() {
        return hasNext;
    }

    public double[] getValues() {
        if (array == null) {
            array = new double[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = list.get(i);
            }
        }
        return array;
    }
}
