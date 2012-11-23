package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.stab;

import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.Mitarbeiter;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.MitarbeiterGehalt;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.MitarbeiterRessourceGroupAssoc;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.person.Person;
import org.rapidpm.persistence.system.security.Benutzer;

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
    private Benutzer benutzer;

    public MitarbeiterBuilder setPerson(final Person person) {
        this.person = person;
        return this;
    }

    public MitarbeiterBuilder setRessourcenGruppe(final RessourceGroup ressourcenGruppe) {
        this.ressourcenGruppe = ressourcenGruppe;
        return this;
    }

    public MitarbeiterBuilder setJahresGehalt(final float jahresGehalt) {
        this.jahresGehalt = jahresGehalt;
        return this;
    }

    public MitarbeiterBuilder setBenutzer(final Benutzer benutzer) {
        this.benutzer = benutzer;
        return this;
    }

    public Mitarbeiter getMitarbeiter(){
        if(person == null || ressourcenGruppe == null || jahresGehalt <= 0)
            throw new IllegalStateException("MitarbeiterBuilder: Es wurden nicht alle notwendigen Daten angegegeben");

        final Mitarbeiter mitarbeiter = new Mitarbeiter();
        mitarbeiter.setPerson(person);

        final MitarbeiterRessourceGroupAssoc mitarbeiterRessourcengruppenAssoziation
                = new MitarbeiterRessourceGroupAssoc();
        mitarbeiterRessourcengruppenAssoziation.setMitarbeiter(mitarbeiter);
        mitarbeiterRessourcengruppenAssoziation.setRessourceGroup(ressourcenGruppe);

        final List<MitarbeiterRessourceGroupAssoc> mitarbeiterRessourceGroupAssocsListe = new ArrayList<>();
        mitarbeiterRessourceGroupAssocsListe.add(mitarbeiterRessourcengruppenAssoziation);
        mitarbeiter.setMitarbeiterRessourceGroupAssocListe(mitarbeiterRessourceGroupAssocsListe);

        final MitarbeiterGehalt gehalt = new MitarbeiterGehalt();
        gehalt.setJahresgehalt(jahresGehalt);

        final List<MitarbeiterGehalt> gehaltsListe = new ArrayList<>();
        gehaltsListe.add(gehalt);
        mitarbeiter.setMitarbeiterGehaltsListe(gehaltsListe);

        return mitarbeiter;
    }
}


