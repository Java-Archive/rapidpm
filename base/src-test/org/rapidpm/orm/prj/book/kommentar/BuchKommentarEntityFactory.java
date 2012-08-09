package org.rapidpm.orm.prj.book.kommentar;

import org.rapidpm.orm.EntityFactory;
import org.rapidpm.orm.security.BenutzerEntityFactory;

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
