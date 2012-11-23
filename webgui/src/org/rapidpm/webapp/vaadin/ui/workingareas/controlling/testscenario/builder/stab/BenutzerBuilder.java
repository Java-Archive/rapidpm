package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.stab;

import org.rapidpm.persistence.system.security.Benutzer;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public class BenutzerBuilder {

    private String login;
    private String passwd;
    private String email;

    public BenutzerBuilder setLogin(final String login) {
        this.login = login;
        return this;
    }

    public BenutzerBuilder setPasswd(final String passwd) {
        this.passwd = passwd;
        return this;
    }

    public BenutzerBuilder setEmail(final String email) {
        this.email = email;
        return this;
    }

    public Benutzer getBenutzer(){
        if(login.isEmpty()
                || passwd.isEmpty()
                || email.isEmpty())
            throw new IllegalStateException("BenutzerBuilder: nicht alle benötigten Daten angegeben:");

        final Benutzer benutzer = new Benutzer();
        benutzer.setLogin(login);
        benutzer.setPasswd(passwd);
        benutzer.setEmail(email);
        return benutzer;
    }
}
