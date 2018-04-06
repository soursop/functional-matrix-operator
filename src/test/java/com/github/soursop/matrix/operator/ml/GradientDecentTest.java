package com.github.soursop.matrix.operator.ml;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GradientDecentTest {

    @Test
    public void testSingle() {
        List<String> list = new ArrayList<String>() {{
            add("aaaa");
            add("bbbb");
        }};
        ImmutableList<String> strings = FluentIterable.from(list)
                .transform(new Function<String, String>() {
                    @Override
                    public String apply(String input) {
                        return input.substring(2);
                    }
                }).toList();
        System.out.println(strings);
    }
}
