package com.github.soursop.matrix.operator.hadoop;

import com.github.soursop.matrix.operator.TextIterator;
import org.junit.Test;

import java.io.DataInput;
import java.io.IOException;

import static com.github.soursop.matrix.operator.utils.Utils.from;

/**
 * @author soursop
 * @created 2018. 4. 13.
 */
public class HadoopMatrixTest {

    @Test
    public void test() throws IOException {
        DataInput from = from("hadoop/stackoverflow/part-00000");
        TextIterator iterator = new TextIterator(from);
        for (int i = 0; i < 100; i++) {
            String next = iterator.next();
            System.out.println(String.format("%d,%d:\t%s", iterator.height(), iterator.width(), next));
        }
    }
    
}
