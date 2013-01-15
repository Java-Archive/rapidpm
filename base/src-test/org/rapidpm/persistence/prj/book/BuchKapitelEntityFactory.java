package org.rapidpm.persistence.prj.book;

import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.prj.book.kommentar.BuchKapitelKommentarEntityFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 09:22
 */
public class BuchKapitelEntityFactory extends EntityFactory<BuchKapitel> {

    public BuchKapitelEntityFactory() {
        super(BuchKapitel.class);
    }

    @Override
    public BuchKapitel createRandomEntity() {
        final BuchKapitel kapitel = new BuchKapitel();
        kapitel.setFreigeschaltet(RND.nextBoolean());
        kapitel.setKapitelnummer(RND.nextInt(1, 10));
        kapitel.setUeberschrift(RND.nextSentence(1, 4, 5, 15));
        kapitel.setUntertitel(RND.nextSentence(0, 6, 2, 12));
        //kapitel.setSeitenliste(new BuchSeiteEntityFactory().createRandomEntityList(10, 100));
        kapitel.setKapitelkommentarliste(new BuchKapitelKommentarEntityFactory().createRandomEntityList(0, 5));
        return kapitel;
    }
}
