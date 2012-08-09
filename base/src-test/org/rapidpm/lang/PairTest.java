package org.rapidpm.lang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 12.10.11
 * Time: 12:28
 */
public class PairTest {
    @Test
    public void testList() throws Exception {
        final List<Pair<Class<?>, String>> pairs = new ArrayList<Pair<Class<?>, String>>();
        final Pair pair = Pair.create("www", "bbb");
        final Pair pair1 = Pair.create("WWW".getClass(), 100L);

        final Pair www = Pair.create("WWW".getClass(), "WWW");
        pairs.add(www);
//        pairs.add(Pair.create(Pair.class, "WWW"));
//        pairs.add(Pair.create("WWW".getClass(), "WWW"));
    }
}