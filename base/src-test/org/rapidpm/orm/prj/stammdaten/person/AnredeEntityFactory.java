package org.rapidpm.orm.prj.stammdaten.person;

import org.rapidpm.orm.DataSetEntityFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 12.10.11
 * Time: 13:36
 */
public class AnredeEntityFactory extends DataSetEntityFactory<Anrede, String> {

    public AnredeEntityFactory() {
        super(Anrede.class, "Herr", "Frau", "Nothing");
    }

    @Override
    public Anrede createRandomEntity() {
        final Anrede anrede = new Anrede();
        anrede.setAnrede(combineRandomStringWithNextIndex(dataSet));
        return anrede;
    }
}
