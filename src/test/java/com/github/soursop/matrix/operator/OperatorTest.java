package com.github.soursop.matrix.operator;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author soursop
 * @created 2018. 4. 24.
 */
public class OperatorTest {

    @Ignore
    @Test
    public void testIteratorPerformance() {
        DoubleRandomIterator b = new DoubleRandomIterator(25, 5000, 0);
        DoubleRandomIterator c = new DoubleRandomIterator(5000, 401, 0);
        DoubleMatrix d = new DoubleIterator(1, 25, 5000);
        DoubleMatrix e = new DoubleIterator(1, 5000, 401);
        DenseDoubleMatrix g = DenseDoubleMatrix.of(b.width(), b.values());
        DenseDoubleMatrix h = DenseDoubleMatrix.of(c.width(), c.values());

        // burn up
        for (int i = 0; i < 20; i++) {
            g.product(h).invoke();
            d.product(e).invoke();
        }

        long s1 = System.currentTimeMillis();
        DoubleMatrix invoke1 = g.product(h).invoke();
        System.out.println("elaps time 1: " + (System.currentTimeMillis() - s1) + " > " + invoke1.asSimple(0));

        long s2 = System.currentTimeMillis();
        DoubleMatrix invoke3 = d.product(e).invoke();
        System.out.println("elaps time 2: " + (System.currentTimeMillis() - s2) + " > " + invoke3.asSimple(0));
    }

}
