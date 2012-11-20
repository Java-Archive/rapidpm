package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.stab;

import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.Mitarbeiter;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.MitarbeiterGehalt;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.MitarbeiterRessourceGroupAssoc;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.person.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */
public class MitarbeiterBuilder {
    private Person person;
    private RessourceGroup ressourcenGruppe;
    private float jahresGehalt;

    public MitarbeiterBuilder setPerson(Person person) {
        this.person = person;
        return this;
    }

    public MitarbeiterBuilder setRessourcenGruppe(RessourceGroup ressourcenGruppe) {
        this.ressourcenGruppe = ressourcenGruppe;
        return this;
    }

    public MitarbeiterBuilder setJahresGehalt(float jahresGehalt) {
        this.jahresGehalt = jahresGehalt;
        return this;
    }

    public Mitarbeiter getMitarbeiter(){
        if(person == null || ressourcenGruppe == null || jahresGehalt <= 0)
            throw new IllegalStateException("MitarbeiterBuilder: Es wurden nicht alle notwendigen Daten angegegeben");

        Mitarbeiter mitarbeiter = new Mitarbeiter();
        mitarbeiter.setPerson(person);

        MitarbeiterRessourceGroupAssoc mitarbeiterRessourcengruppenAssoziation = new MitarbeiterRessourceGroupAssoc();
        mitarbeiterRessourcengruppenAssoziation.setMitarbeiter(mitarbeiter);
        mitarbeiterRessourcengruppenAssoziation.setRessourceGroup(ressourcenGruppe);

        List<MitarbeiterRessourceGroupAssoc> mitarbeiterRessourceGroupAssocsListe = new ArrayList<>();
        mitarbeiterRessourceGroupAssocsListe.add(mitarbeiterRessourcengruppenAssoziation);
        mitarbeiter.setMitarbeiterRessourceGroupAssocListe(mitarbeiterRessourceGroupAssocsListe);

        MitarbeiterGehalt gehalt = new MitarbeiterGehalt();
        gehalt.setJahresgehalt(jahresGehalt);
        List<MitarbeiterGehalt> gehaltsListe = new ArrayList<>();
        gehaltsListe.add(gehalt);
        mitarbeiter.setMitarbeiterGehaltsListe(gehaltsListe);

        return mitarbeiter;
    }
}


