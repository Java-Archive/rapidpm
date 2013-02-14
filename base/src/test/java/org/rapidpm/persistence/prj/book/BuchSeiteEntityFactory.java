package org.rapidpm.persistence.prj.book;

import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.prj.book.kommentar.BuchSeitenKommentarEntityFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 09:22
 */
//public class BuchSeiteEntityFactory extends EntityFactory<BuchSeite> {
//
//    public BuchSeiteEntityFactory() {
//        super(BuchSeite.class);
//    }
//
//    @Override
//    public BuchSeite createRandomEntity() {
//        final BuchSeite seite = new BuchSeite();
//        seite.setFreigeschaltet(RND.nextBoolean());
//        seite.setSeitennummer(RND.nextInt(1, 1000));
//        seite.setKommentarliste(new BuchSeitenKommentarEntityFactory().createRandomEntityList(0, 3));
//        seite.setAbsatzliste(new BuchAbsatzEntityFactory().createRandomEntityList(1, 4));
//        seite.setFusnotenliste(new BuchSeitenFussnoteEntityFactory().createRandomEntityList(0, 2));
//        return seite;
//    }
//}
