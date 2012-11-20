package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.stab;

import org.rapidpm.persistence.prj.stammdaten.person.Person;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.stab.PersonBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */
public class PersonenDemoDaten {

    private Person ursulaBecker;
    private Person karlSchmidt;
    private Person peterMueller;
    private Person danielMacDonald;

    public PersonenDemoDaten() {
        ursulaBecker = new PersonBuilder()
                .setWeiblich()
                .setName("Ursula", "Becker")
                .getPerson();
        karlSchmidt = new PersonBuilder()
                .setMaennlich()
                .setName("Karl","Schmidt")
                .getPerson();

        peterMueller = new PersonBuilder()
                .setMaennlich()
                .setName("Peter", "Müller")
                .getPerson();

        danielMacDonald = new PersonBuilder()
                .setMaennlich()
                .setName("Daniel", "MacDonald")
                .getPerson();
    }

    public Person getUrsulaBecker() {
        return ursulaBecker;
    }

    public Person getKarlSchmidt() {
        return karlSchmidt;
    }

    public Person getPeterMueller() {
        return peterMueller;
    }

    public Person getDanielMacDonald() {
        return danielMacDonald;
    }
}
