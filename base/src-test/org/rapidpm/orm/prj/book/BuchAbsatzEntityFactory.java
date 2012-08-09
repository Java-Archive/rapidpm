package org.rapidpm.orm.prj.book;

import org.rapidpm.RandomGenerator;
import org.rapidpm.orm.EntityFactory;
import org.rapidpm.orm.prj.book.kommentar.BuchAbsatzKommentarEntityFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 09:26
 */
public class BuchAbsatzEntityFactory extends EntityFactory<BuchAbsatz> {

    public BuchAbsatzEntityFactory() {
        super(BuchAbsatz.class);
    }

    @Override
    public BuchAbsatz createRandomEntity() {
        final RandomGenerator rnd = RandomGenerator.getInstance();
        final BuchAbsatz absatz = new BuchAbsatz();
        absatz.setFreigeschaltet(RND.nextBoolean());
        absatz.setAbsatznummer(rnd.nextInt(1, 100));
        absatz.setText(rnd.nextSentence(10, 100, 2, 12));
        absatz.setKommentarliste(new BuchAbsatzKommentarEntityFactory().createRandomEntityList(0, 1));
        return absatz;
    }
}
