package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.stab;

import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.Mitarbeiter;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.stab.MitarbeiterBuilder;

import java.util.ArrayList;
import java.util.List;

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
    private List<Mitarbeiter> mitarbeiterList = new ArrayList<>();

    private PersonenDemoDaten personenDemoDaten = new PersonenDemoDaten();
    private RessourcengruppenDemoDaten ressourcengruppenDemoDaten = new RessourcengruppenDemoDaten();
    private BenutzerDemoDaten benutzerDemoDaten = new BenutzerDemoDaten();

    public MitarbeiterDemoDaten(){
        ursulaBeckerMA = new MitarbeiterBuilder()
                .setJahresGehalt(72_000)
                .setPerson(personenDemoDaten.getUrsulaBecker())
                .setRessourcenGruppe(ressourcengruppenDemoDaten.getScrumMaster())
                .setBenutzer(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .getMitarbeiter();

        karlSchmidtMA = new MitarbeiterBuilder()
                .setJahresGehalt(72_000)
                .setPerson(personenDemoDaten.getKarlSchmidt())
                .setRessourcenGruppe(ressourcengruppenDemoDaten.getSeniorProjectCollaborator())
                .setBenutzer(benutzerDemoDaten.getKarlSchmidtBenutzer())
                .getMitarbeiter();

        peterMuellerMA = new MitarbeiterBuilder()
                .setJahresGehalt(45_000)
                .setPerson(personenDemoDaten.getPeterMueller())
                .setRessourcenGruppe(ressourcengruppenDemoDaten.getJuniorProjectCollaborator())
                .setBenutzer(benutzerDemoDaten.getPeterMuellerBenutzer())
                .getMitarbeiter();

        danielMacDonaldMA = new MitarbeiterBuilder()
                .setJahresGehalt(17_000)
                .setPerson(personenDemoDaten.getDanielMacDonald())
                .setRessourcenGruppe(ressourcengruppenDemoDaten.getWorkingStudent())
                .setBenutzer(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .getMitarbeiter();

        mitarbeiterList.add(ursulaBeckerMA);
        mitarbeiterList.add(karlSchmidtMA);
        mitarbeiterList.add(peterMuellerMA);
        mitarbeiterList.add(danielMacDonaldMA);
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

    public List<Mitarbeiter> getMitarbeiterList() {
        return mitarbeiterList;
    }
}
