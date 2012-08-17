package org.rapidpm.persistence.prj.stammdaten.address;

import org.rapidpm.persistence.EntityFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 05.10.11
 * Time: 10:10
 */
public class PostfachFactory extends EntityFactory<Postfach> {
    public PostfachFactory() {
        super(Postfach.class);
    }

    public Postfach createPostfach(final int postfachnummer, final String notiz) {
        final Postfach postfach = new Postfach();
        postfach.setPostfachnummer(String.valueOf(postfachnummer));
        postfach.setNotiz(notiz);
        return postfach;
    }

    public Postfach createPostfach(final String postfachnummer, final String notiz) {
        final Postfach postfach = new Postfach();
        postfach.setPostfachnummer(postfachnummer);
        postfach.setNotiz(notiz);
        return postfach;
    }

    public List<Postfach> createRandomPostfaecher(final int count) {
        final List<Postfach> postfaecher = new ArrayList<Postfach>(count);
        for (int i = 0; i < count; i++) {
            postfaecher.add(createRandomEntity());
        }
        return postfaecher;
    }

    @Override
    public Postfach createRandomEntity() {
        return createPostfach(nextIndex(), RND.nextSentence(10, 15, 3, 6));
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
    }
}
