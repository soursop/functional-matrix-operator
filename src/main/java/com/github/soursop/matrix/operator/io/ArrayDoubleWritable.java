package com.github.soursop.matrix.operator.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.Writable;

public class ArrayDoubleWritable implements Writable {
    private double[] array;

    public ArrayDoubleWritable() {
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        int length = in.readInt();
        array = new double[length];
        for (int i = 0; i < length; i++) {
            array[i] = in.readDouble();
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(array.length);
        for (int i = 0; i < array.length; i++) {
            out.writeDouble(array[i]);
        }
    }

    /**
     * Returns a deep copy of the array.
     */
    public double[] getClone() {
        double[] copy = new double[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        return copy;
    }

    /**
     * Returns a reference to the underlying array.
     */
    public double[] getArray() {
        return array;
    }

    /**
     * Sets the underlying array.
     */
    public void setArray(double[] array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}