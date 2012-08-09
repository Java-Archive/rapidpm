package org.rapidpm.orm.security;

import org.rapidpm.orm.DataSetEntityFactory;
import org.rapidpm.orm.system.security.BenutzerGruppe;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 11:45
 */
public class BenutzerGruppeEntityFactory extends DataSetEntityFactory<BenutzerGruppe, String> {

    public BenutzerGruppeEntityFactory() {
        super(BenutzerGruppe.class, "Gast", "Benutzer", "Administrator");
    }

    @Override
    public BenutzerGruppe createRandomEntity() {
        final BenutzerGruppe benutzerGruppe = new BenutzerGruppe();
        benutzerGruppe.setGruppenname(combineRandomStringWithNextIndex(dataSet));
        return benutzerGruppe;
    }
}