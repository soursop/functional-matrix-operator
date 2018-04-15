package com.github.soursop.matrix.operator;

import com.google.common.base.Splitter;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextIterator implements Matrix, Iterator<String> {
    private int height = 0;
    private int width = 0;
    private int size = 0;
    private final DataInput in;
    private boolean hasNext = true;
    private String[] values = new String[0];

    public TextIterator(DataInput in) {
        this.in = in;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int width() {
        return (size - 1) % width;
    }

    @Override
    public int size() {
        return size;
    }

    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public String next() {
        if (width == 0 || size % width == 0) {
            String line = null;
            try {
                line = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line == null) {
                hasNext = false;
                return "";
            }
            Iterable<String> split = Splitter.on(",").split(line);
            if (width == 0) {
                List<String> list = new ArrayList<>();
                for (String v : split) {
                    list.add(v);
                }
                width = list.size();
                values = list.toArray(new String[width]);
            } else {
                int i = 0;
                for (String v : split) {
                    values[i++] = v;
                }
            }
            height = height + 1;
        }
        size = size + 1;
        return values[(size - 1) % width];
    }

    @Override
    public void remove() {
        Assert.assertUnsupportedOperation();
    }
}
