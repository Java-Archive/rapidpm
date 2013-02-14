package org.rapidpm.persistence.security.berechtigungen;

import org.rapidpm.persistence.DataSetEntityFactory;
import org.rapidpm.persistence.system.security.berechtigungen.Berechtigung;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 11:41
 */
public class BerechtigungEntityFactory extends DataSetEntityFactory<Berechtigung, String> {

    public BerechtigungEntityFactory() {
        super(Berechtigung.class, "alle", "keine");  // TODO
    }

    @Override
    public Berechtigung createRandomEntity() {
        final Berechtigung berechtigung = new Berechtigung();
        berechtigung.setName(combineRandomStringWithNextIndex(dataSet));
        return berechtigung;
    }

}