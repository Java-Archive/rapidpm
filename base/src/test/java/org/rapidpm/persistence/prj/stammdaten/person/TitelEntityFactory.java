package org.rapidpm.persistence.prj.stammdaten.person;

import org.rapidpm.persistence.DataSetEntityFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 12.10.11
 * Time: 13:41
 */
public class TitelEntityFactory extends DataSetEntityFactory<Titel, String> {

    public TitelEntityFactory() {
        super(Titel.class, "Prof.", "Dr.", "Dipl.-Ing.");
    }

    @Override
    public Titel createRandomEntity() {
        final Titel titel = new Titel();
        titel.setTitelNr(1);
        titel.setTitel(combineRandomStringWithNextIndex(dataSet));
        return titel;
    }
}
