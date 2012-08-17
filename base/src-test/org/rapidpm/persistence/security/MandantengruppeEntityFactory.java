package org.rapidpm.persistence.security;

import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.system.security.Mandantengruppe;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 11:46
 */
public class MandantengruppeEntityFactory extends EntityFactory<Mandantengruppe> {

    public MandantengruppeEntityFactory() {
        super(Mandantengruppe.class);
    }

    @Override
    public Mandantengruppe createRandomEntity() {
        final Mandantengruppe mandantengruppe = new Mandantengruppe();
        mandantengruppe.setMandantengruppe(RND.nextLetterString(8, 16));
        return mandantengruppe;
    }
}