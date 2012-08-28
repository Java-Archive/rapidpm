package org.rapidpm.persistence.book;

import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.book.kommentar.BuchKommentarEntityFactory;
import org.rapidpm.persistence.security.BenutzerEntityFactory;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 09:21
 */
public class BuchEntityFactory extends EntityFactory<Buch> {

    public BuchEntityFactory() {
        super(Buch.class);
    }

    @Override
    public Buch createRandomEntity() {
        final Buch buch = new Buch();
        buch.setTitel(RND.nextSentence(1, 5, 5, 12));
        buch.setUntertitel(RND.nextSentence(0, 5, 2, 12));
        buch.setSummary(RND.nextSentence(50, 250, 2, 15));
        buch.setVersion(String.valueOf(RND.nextDigit()));
        buch.setBuchKapitelListe(new BuchKapitelEntityFactory().createRandomEntityList(3, 10));
        buch.setAutorenliste(new BenutzerEntityFactory().createRandomEntityList(1, 3));
        buch.setLeserliste(new BenutzerEntityFactory().createRandomEntityList(0, 10));
        buch.setKommentarliste(new BuchKommentarEntityFactory().createRandomEntityList(0, 10));
        return buch;
    }

    @Test
    public void test() throws Exception {
        final Buch buch = new BuchEntityFactory().createRandomEntity();
        assertNotNull(buch);
        assertNotNullOrEmpty(buch.getTitel());
        assertNotNull(buch.getUntertitel());
        assertNotNullOrEmpty(buch.getSummary());
        assertNotNullOrEmpty(buch.getVersion());
        assertNotNullOrEmpty(buch.getKommentarliste());
        assertNotNullOrEmpty(buch.getAutorenliste());
    }

    private static void assertNotNullOrEmpty(final Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertNotNull(object);
        assertFalse((Boolean) object.getClass().getMethod("isEmpty").invoke(object));
    }
}
