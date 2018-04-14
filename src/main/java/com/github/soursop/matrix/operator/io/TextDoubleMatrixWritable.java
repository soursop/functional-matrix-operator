package com.github.soursop.matrix.operator.io;

import com.github.soursop.matrix.operator.DoubleMatrix;
import com.github.soursop.matrix.operator.MutableDoubleMatrix;
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
public class TextDoubleMatrixWritable implements Writable {
    private List<Double> list = new ArrayList<>();
    private final MutableDoubleMatrix matrix = new MutableDoubleMatrix(new double[0]);
    private boolean hasNext = true;

    public void setValues(DoubleMatrix other) {
        matrix.set(other.width(), other.values());
    }

    public void setValues(double[] array) {
        matrix.set(array);
    }

    public void setValues(int width, double[] array) {
        matrix.set(width, array);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        for (int i = 0; i < matrix.size(); i++) {
            out.writeBytes(String.valueOf(matrix.valueOf(i)));
            if (++i < matrix.size()) {
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
            list.add(s.isEmpty()? 0l : Double.valueOf(s));
        }
    }

    public boolean hasNext() {
        return hasNext;
    }

    public MutableDoubleMatrix getValues() {
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        matrix.set(array);
        list.clear();
        return matrix;
    }
}
