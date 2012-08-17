package org.rapidpm.persistence.prj.book.kommentar;

import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.security.BenutzerEntityFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 13.10.11
 * Time: 12:33
 */
public class BuchKapitelKommentarEntityFactory extends EntityFactory<BuchKapitelKommentar> {

    public BuchKapitelKommentarEntityFactory() {
        super(BuchKapitelKommentar.class);
    }

    @Override
    public BuchKapitelKommentar createRandomEntity() {
        final BuchKapitelKommentar kommentar = new BuchKapitelKommentar();
        kommentar.setKommentar(RND.nextSentence(3, 20, 2, 12));
        kommentar.setDatum(RND.nextDate());
        kommentar.setKommentator(new BenutzerEntityFactory().createRandomEntity());
        return kommentar;
    }
}
