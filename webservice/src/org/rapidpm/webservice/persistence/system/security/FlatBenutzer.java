package org.rapidpm.webservice.persistence.system.security;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.berechtigungen.RolleDAO;
import org.rapidpm.webservice.mapping.FlatEntity;

import java.util.Set;

/**
 * User: Alexander Vos
 * Date: 03.12.12
 * Time: 15:08
 */
public class FlatBenutzer extends FlatEntity<Benutzer> {
    // TODO
    private String login;
    private String email;
    private Set<Long> rolleIds;

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

    public Set<Long> getRolleIds() {
        return rolleIds;
    }

    public void setRolleIds(final Set<Long> rolleIds) {
        this.rolleIds = rolleIds;
    }

    @Override
    public void fromEntity(final Benutzer benutzer) {
        id = benutzer.getId();
        login = benutzer.getLogin();
        email = benutzer.getEmail();
        rolleIds = entitiesToIds(benutzer.getRollen());
    }

    @Override
    public void toEntity(final Benutzer benutzer, final DaoFactory daoFactory) {
        benutzer.setLogin(login);
        benutzer.setEmail(email);
        final RolleDAO rolleDAO = daoFactory.getRolleDAO();
        benutzer.setRollen(rolleDAO.loadWithOIDSet(rolleIds));
    }
}
