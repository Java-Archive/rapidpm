package org.rapidpm.webservice.persistence.system.security;

import org.rapidpm.webservice.mapping.FlatEntity;

/**
 * User: Alexander Vos
 * Date: 03.12.12
 * Time: 15:08
 */
public class FlatBenutzer extends FlatEntity {
    // TODO
    private String login;
    private String email;

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
}
