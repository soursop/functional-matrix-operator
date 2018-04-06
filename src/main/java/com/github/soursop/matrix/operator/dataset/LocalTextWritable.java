package com.github.soursop.matrix.operator.dataset;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author soursop
 * @created 2018. 4. 6.
 */
public class LocalTextWritable implements Writable {
    private double[] doubles;

    @Override
    public void write(DataOutput out) throws IOException {

    }

    @Override
    public void readFields(DataInput in) throws IOException {

    }
}
