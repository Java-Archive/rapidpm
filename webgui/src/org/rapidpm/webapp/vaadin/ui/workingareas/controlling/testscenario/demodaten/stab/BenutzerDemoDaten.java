package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.stab;

import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.stab.BenutzerBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public class BenutzerDemoDaten {

    private Benutzer ursulaBeckerBenutzer;
    private Benutzer karlSchmidtBenutzer;
    private Benutzer peterMuellerBenutzer;
    private Benutzer danielMacDonaldBenutzer;
    
    public BenutzerDemoDaten(){
        ursulaBeckerBenutzer = new BenutzerBuilder()
                .setLogin("ursula.becker")
                .setEmail("ursula.becker@rapidpm.org")
                .setPasswd("geheim")
                .getBenutzer();

        karlSchmidtBenutzer = new BenutzerBuilder()
                .setLogin("karl.schmidt")
                .setEmail("karl.schmidt@rapidpm.org")
                .setPasswd("geheim")
                .getBenutzer();

        peterMuellerBenutzer = new BenutzerBuilder()
                .setLogin("peter.mueller")
                .setEmail("peter.mueller@rapidpm.org")
                .setPasswd("geheim")
                .getBenutzer();

        danielMacDonaldBenutzer = new BenutzerBuilder()
                .setLogin("daniel.macdonald")
                .setEmail("daniel.macdonald@rapdpm.org")
                .setPasswd("geheim")
                .getBenutzer();

    }

    public Benutzer getUrsulaBeckerBenutzer() {
        return ursulaBeckerBenutzer;
    }

    public Benutzer getKarlSchmidtBenutzer() {
        return karlSchmidtBenutzer;
    }

    public Benutzer getPeterMuellerBenutzer() {
        return peterMuellerBenutzer;
    }

    public Benutzer getDanielMacDonaldBenutzer() {
        return danielMacDonaldBenutzer;
    }

}
