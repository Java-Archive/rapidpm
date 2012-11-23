package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.stab;

import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 19.11.12
 * Time: 19:28
 * To change this template use File | Settings | File Templates.
 */
public class RessourceGroupBuilder {

    private double bruttoJahresGehalt;
    private double  stundenProJahrm = 1840.0;
    private int stundenProWoche = 40;
    private double fakturierbar = 0.75;
    private String ressourcenGruppenName;


    public RessourceGroupBuilder setBruttoJahresGehalt(final double bruttoJahresGehalt) {
        this.bruttoJahresGehalt = bruttoJahresGehalt;
        return this;
    }

    public RessourceGroupBuilder setStundenProJahrm(final double stundenProJahrm) {
        this.stundenProJahrm = stundenProJahrm;
        return this;
    }

    public RessourceGroupBuilder setStundenProWoche(final int stundenProWoche) {
        this.stundenProWoche = stundenProWoche;
        return this;
    }

    public RessourceGroupBuilder setFakturierbar(final double fakturierbar) {
        this.fakturierbar = fakturierbar;
        return this;
    }

    public RessourceGroupBuilder setRessourcenGruppenName(final String ressourcenGruppenName) {
        this.ressourcenGruppenName = ressourcenGruppenName;
        return this;
    }

    public RessourceGroup getRessourceGroup(){
        if(ressourcenGruppenName.isEmpty() || ressourcenGruppenName == null)
            throw new IllegalArgumentException("RessourceGroupBuilder: Keine Name angegeben");

        if(bruttoJahresGehalt == 0)
            throw new IllegalArgumentException("RessourceGroupBuilder: Keine brutto Jahresgehalt angegeben");

        final RessourceGroup ressourcenGruppe = new RessourceGroup();
        ressourcenGruppe.setBruttoGehalt(bruttoJahresGehalt);
        ressourcenGruppe.setExternalEurosPerHour(stundenProJahrm);
        ressourcenGruppe.setHoursPerWeek(stundenProWoche);
        ressourcenGruppe.setFacturizable(fakturierbar);
        ressourcenGruppe.setName(ressourcenGruppenName);
        return ressourcenGruppe;
    }


}
