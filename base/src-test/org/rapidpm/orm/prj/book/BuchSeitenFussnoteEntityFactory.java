package org.rapidpm.orm.prj.book;

import org.rapidpm.RandomGenerator;
import org.rapidpm.orm.EntityFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 09:22
 */
public class BuchSeitenFussnoteEntityFactory extends EntityFactory<BuchSeitenFussnote> {

    public BuchSeitenFussnoteEntityFactory() {
        super(BuchSeitenFussnote.class);
    }

    @Override
    public BuchSeitenFussnote createRandomEntity() {
        final RandomGenerator rnd = RandomGenerator.getInstance();
        final BuchSeitenFussnote fussnote = new BuchSeitenFussnote();
        fussnote.setFussnotentext(rnd.nextSentence(3, 6, 2, 12));
        fussnote.setFussnotenzeichen(String.valueOf(rnd.nextInt(1, 99)));
        return fussnote;
    }
}
