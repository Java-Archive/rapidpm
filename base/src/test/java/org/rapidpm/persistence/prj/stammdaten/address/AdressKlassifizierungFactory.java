package org.rapidpm.persistence.prj.stammdaten.address;

import org.rapidpm.persistence.EntityFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 05.10.11
 * Time: 10:09
 */
public class AdressKlassifizierungFactory extends EntityFactory<AdressKlassifizierung> {
    private final static List<String> klassifizierungen = new ArrayList<String>();

    public AdressKlassifizierungFactory() {
        super(AdressKlassifizierung.class);
    }

    static {
        klassifizierungen.add("privat");
        klassifizierungen.add("geschäftlich");
        klassifizierungen.add("ehrenamtlich");
        klassifizierungen.add("öffentlich");
    }

    public static List<String> getKlassifizierungen() {
        return klassifizierungen;
    }

    @Override
    public AdressKlassifizierung createRandomEntity() {
        final AdressKlassifizierung klassifizierung = new AdressKlassifizierung();
        klassifizierung.setKlassifizierung(combineRandomStringWithNextIndex(klassifizierungen));
        return klassifizierung;
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
    }
}
