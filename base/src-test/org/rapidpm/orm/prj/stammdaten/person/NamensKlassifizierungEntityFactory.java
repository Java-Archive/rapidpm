package org.rapidpm.orm.prj.stammdaten.person;

import org.rapidpm.orm.DataSetEntityFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 12.10.11
 * Time: 13:39
 */
public class NamensKlassifizierungEntityFactory extends DataSetEntityFactory<NamensKlassifizierung, String> {
    public static final String VORNAME = "Vorname";
    public static final String NACHNAME = "Nachname";

    public NamensKlassifizierungEntityFactory() {
        super(NamensKlassifizierung.class, VORNAME, NACHNAME);
    }

    @Override
    public NamensKlassifizierung createRandomEntity() {
        final NamensKlassifizierung namensKlassifizierung = new NamensKlassifizierung();
        namensKlassifizierung.setKlassifizierung(combineRandomStringWithNextIndex(dataSet));
        return namensKlassifizierung;
    }

    public NamensKlassifizierung createVornameKlassifizierung() {
        final NamensKlassifizierung namensKlassifizierung = new NamensKlassifizierung();
        namensKlassifizierung.setKlassifizierung(VORNAME);
        return namensKlassifizierung;
    }

    public NamensKlassifizierung createNachnameKlassifizierung() {
        final NamensKlassifizierung namensKlassifizierung = new NamensKlassifizierung();
        namensKlassifizierung.setKlassifizierung(NACHNAME);
        return namensKlassifizierung;
    }
}
