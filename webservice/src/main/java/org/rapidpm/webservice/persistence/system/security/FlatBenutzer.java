package org.rapidpm.webservice.persistence.system.security;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.berechtigungen.Rolle;
import org.rapidpm.webservice.mapping.FlatEntity;

import java.util.Set;

public class FlatBenutzer extends FlatEntity<Benutzer> {
    // TODO
    private String login;
    private String email;
    private Set<Rolle> rollen;

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Set<Rolle> getRollen() {
        return rollen;
    }

    public void setRollen(final Set<Rolle> rollen) {
        this.rollen = rollen;
    }

    @Override
    public void fromEntity(final Benutzer benutzer) {
        id = benutzer.getId();
        login = benutzer.getLogin();
        email = benutzer.getEmail();
        rollen = benutzer.getRollen();
    }

    @Override
    public void toEntity(final Benutzer benutzer, final DaoFactory daoFactory) {
        benutzer.setLogin(login);
        benutzer.setEmail(email);
        benutzer.setRollen(rollen);
    }
}
