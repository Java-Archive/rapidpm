package org.rapidpm.persistence.book.kommentar;

import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.book.kommentar.BuchKommentar;
import org.rapidpm.persistence.security.BenutzerEntityFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 13.10.11
 * Time: 12:33
 */
public class BuchKommentarEntityFactory extends EntityFactory<BuchKommentar> {

    public BuchKommentarEntityFactory() {
        super(BuchKommentar.class);
    }

    @Override
    public BuchKommentar createRandomEntity() {
        final BuchKommentar kommentar = new BuchKommentar();
        kommentar.setKommentar(RND.nextSentence(3, 20, 2, 12));
        kommentar.setDatum(RND.nextDate());
        kommentar.setKommentator(new BenutzerEntityFactory().createRandomEntity());
        return kommentar;
    }
}
