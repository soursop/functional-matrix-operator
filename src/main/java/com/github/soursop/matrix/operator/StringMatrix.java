package com.github.soursop.matrix.operator;

/**
 * @author soursop
 * @created 2018. 4. 9.
 */
public class StringMatrix {
    private final int width;
    private final int height;
    private final String[] values;

    public StringMatrix(int height, int width) {
        this.width = width;
        this.height = height;
        values = new String[height * width];
    }

    public void set(int h, int w, String value) {
        String s = valueOf(h, w);
        if (s == null) {
            s = value;
        } else {
            s += value;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                builder.append(valueOf(h, w));
                if (w + 1 < width) {
                    builder.append(",");
                }
            }
            if (h + 1 < height) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private String valueOf(int h, int w) {
        return values[h * width + w];
    }

}
