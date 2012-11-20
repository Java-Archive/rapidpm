package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.stab;

import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.Mitarbeiter;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.stab.MitarbeiterBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */
public class MitarbeiterDemoDaten {

    private Mitarbeiter ursulaBeckerMA;
    private Mitarbeiter karlSchmidtMA;
    private Mitarbeiter peterMuellerMA;
    private Mitarbeiter danielMacDonaldMA;

    private PersonenDemoDaten personenDemoDaten;
    private RessourcengruppenDemoDaten ressourcengruppenDemoDaten;

    public void initMitarbeiter(){
        ursulaBeckerMA = new MitarbeiterBuilder()
                .setJahresGehalt(72_000)
                .setPerson(personenDemoDaten.getUrsulaBecker())
                .setRessourcenGruppe(ressourcengruppenDemoDaten.getScrumMaster())
                .getMitarbeiter();

        karlSchmidtMA = new MitarbeiterBuilder()
                .setJahresGehalt(72_000)
                .setPerson(personenDemoDaten.getKarlSchmidt())
                .setRessourcenGruppe(ressourcengruppenDemoDaten.getSeniorProjectCollaborator())
                .getMitarbeiter();

        peterMuellerMA = new MitarbeiterBuilder()
                .setJahresGehalt(45_000)
                .setPerson(personenDemoDaten.getPeterMueller())
                .setRessourcenGruppe(ressourcengruppenDemoDaten.getJuniorProjectCollaborator())
                .getMitarbeiter();

        danielMacDonaldMA = new MitarbeiterBuilder()
                .setJahresGehalt(17_000)
                .setPerson(personenDemoDaten.getDanielMacDonald())
                .setRessourcenGruppe(ressourcengruppenDemoDaten.getWorkingStudent())
                .getMitarbeiter();
    }

    public Mitarbeiter getUrsulaBeckerMA() {
        return ursulaBeckerMA;
    }

    public Mitarbeiter getKarlSchmidtMA() {
        return karlSchmidtMA;
    }

    public Mitarbeiter getPeterMuellerMA() {
        return peterMuellerMA;
    }

    public Mitarbeiter getDanielMacDonaldMA() {
        return danielMacDonaldMA;
    }
}
