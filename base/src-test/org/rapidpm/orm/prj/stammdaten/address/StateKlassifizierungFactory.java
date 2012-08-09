package org.rapidpm.orm.prj.stammdaten.address;

import org.rapidpm.orm.DataSetEntityFactory;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 05.10.11
 * Time: 10:11
 */
public class StateKlassifizierungFactory extends DataSetEntityFactory<StateKlassifizierung, String> {
    public StateKlassifizierungFactory() {
        super(StateKlassifizierung.class, "Bundesland", "Kanton");
    }

    @Override
    public StateKlassifizierung createRandomEntity() {
        final StateKlassifizierung klassifizierung = new StateKlassifizierung();
        klassifizierung.setBezeichnung(combineRandomStringWithNextIndex(dataSet));
        return klassifizierung;
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
    }
}
