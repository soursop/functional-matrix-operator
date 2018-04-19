package com.github.soursop.matrix.operator.utils;

import com.github.soursop.matrix.operator.io.TextDoubleWritable;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public class Utils {

    public static DataInput from(String resource) throws IOException {
        return stream(asUrl(resource));
    }

    public static URL asUrl(String resource) throws IOException {
        return com.google.common.io.Resources.getResource(resource);
    }

    public static DataInput stream(URL url) throws IOException {
        if (url.getFile().endsWith("gz")) {
            InputStream gzipStream = new GZIPInputStream(url.openStream());
            Reader decoder = new InputStreamReader(gzipStream, StandardCharsets.UTF_8);
            BufferedReader buffered = new BufferedReader(decoder);
            InputStream targetStream =
                    IOUtils.toInputStream(IOUtils.toString(buffered), Charsets.UTF_8);
            return new DataInputStream(targetStream);
        } else {
            return new DataInputStream(url.openStream());
        }
    }

    public static double[] gzip(String resource) throws IOException {
        return gzip(asUrl(resource));
    }

    public static double[] gzip(URL url) throws IOException {
        TextDoubleWritable.DoubleBuffer buffer = new TextDoubleWritable.DoubleBuffer();

        InputStream gzipStream = new GZIPInputStream(url.openStream());
        Reader decoder = new InputStreamReader(gzipStream, StandardCharsets.UTF_8);
        BufferedReader buffered = new BufferedReader(decoder);

        String line;
        while ((line = buffered.readLine()) != null) {
            Iterable<String> split = Splitter.on(",").split(line);
            for (String s : split) {
                buffer.add(Double.parseDouble(s));
            }
        }
        buffered.close();
        gzipStream.close();

        return buffer.values();
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

    public static void print(String format, Object... objects) {
        System.out.println(String.format(format, objects));
    }
}
