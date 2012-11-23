
package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.stab;

import org.rapidpm.persistence.prj.stammdaten.person.*;

import java.util.ArrayList;
import java.util.List;

public class PersonBuilder {

    public static final Anrede anredeFrau = new Anrede();
    public static final Anrede anredeHerr = new Anrede();

    public static final Geschlecht geschlechtWeiblich = new Geschlecht();
    public static final Geschlecht geschlechtMaennlich = new Geschlecht();

    public static final NamensKlassifizierung namensKlassifizierungVorname = new NamensKlassifizierung();
    public static final NamensKlassifizierung namensKlassifizierungNachname = new NamensKlassifizierung();
    static {
        anredeFrau.setAnrede("Frau");
        anredeHerr.setAnrede("Herr");

        geschlechtWeiblich.setGeschlecht("weiblich");
        geschlechtMaennlich.setGeschlecht("männlich");

        namensKlassifizierungVorname.setKlassifizierung("Vorname");
        namensKlassifizierungNachname.setKlassifizierung("Nachname");
    }


    private Geschlecht geschlecht;
    private Anrede anrede;
    private String vorname;
    private String nachname;

    public PersonBuilder setWeiblich() {
        geschlecht = geschlechtWeiblich;
        anrede = anredeFrau;
        return this;
    }

    public PersonBuilder setMaennlich(){
        geschlecht = geschlechtMaennlich;
        anrede = anredeHerr;
        return this;
    }

    public PersonBuilder setName(final String vorname, final String nachname){
        this.vorname = vorname;
        this.nachname = nachname;
        return this;
    }

    public Person getPerson() {
        if(geschlecht == null || anrede == null || vorname.isEmpty() || nachname.isEmpty())
            throw new IllegalStateException("PersonBuilder: Es wurden nicht alle Notwendigen Daten angegeben.");

        final PersonenName personNameNachname = new PersonenName();
        personNameNachname.setName(vorname);
        personNameNachname.setReihenfolge(0);
        personNameNachname.setNamensKlassifizierung(namensKlassifizierungVorname);

        final PersonenName personNameVorname = new PersonenName();
        personNameVorname.setName(nachname);
        personNameVorname.setReihenfolge(1);
        personNameVorname.setNamensKlassifizierung(namensKlassifizierungNachname);

        final List<PersonenName> namensListe = new ArrayList<PersonenName>();
        namensListe.add(personNameNachname);
        namensListe.add(personNameVorname);

        final Person person = new Person();
        person.setGeschlecht(geschlecht);
        person.setAnrede(anrede);
        person.setNamen(namensListe);
        return person;
    }
}