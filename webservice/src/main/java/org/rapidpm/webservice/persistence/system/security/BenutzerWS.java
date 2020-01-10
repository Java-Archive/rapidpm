package org.rapidpm.webservice.persistence.system.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webservice.mapping.FlatBaseWS;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Benutzer Web-Service zum Verwalten von Benutzer- und Session-Daten (u.A. Login und Logout).
 */
@WebService(serviceName = "BenutzerWS")
public class BenutzerWS extends FlatBaseWS<Benutzer, BenutzerDAO, FlatBenutzer> {

    public BenutzerWS() {
        super(Benutzer.class, FlatBenutzer.class);
    }

    @Override
    protected BenutzerDAO getDao() {
        return daoFactory.getBenutzerDAO();
    }

    /**
     * Authentifiziert den Benutzer anhand eines Login und Passworts.
     *
     * @param login  Loginname/Benutzername.
     * @param passwd Passwort.
     * @return Das Benutzer-Objekt zu den Logindaten oder <code>null</code>, wenn die Logindaten ungültig sind.
     * @throws AuthenticationException Wenn die Logindaten ungültig sind.
     */
    // TODO hashed passwd, return SessionID
    @WebMethod
    public FlatBenutzer authenticate(@WebParam(name = "login") final String login,
                                     @WebParam(name = "passwd") final String passwd) throws AuthenticationException {
        final Subject user = SecurityUtils.getSubject();
        final Session session = user.getSession(); // new session with every request!?
        if (!user.isAuthenticated()) {
            final UsernamePasswordToken token = new UsernamePasswordToken(login, passwd);
            token.setRememberMe(true);
            try {
                user.login(token); // throws AuthenticationException on failure
            } catch (org.apache.shiro.authc.AuthenticationException e) {
                throw new AuthenticationException("Login failed"); // throw generic failure message instead
            }
        }
        // Authentication successful
        // TODO get Benutzer from Shiro?
        final Benutzer benutzer = dao.authenticate(login, passwd);
        return toFlatEntity(benutzer);
    }

    /**
     * Meldet den aktuellen Benutzer ab und löscht die Session.
     */
    @WebMethod
    public void logout() {
        final Subject user = SecurityUtils.getSubject();
        user.logout();
    }

    public static class AuthenticationException extends RuntimeException {
        public AuthenticationException(String message) {
            super(message);
        }
    }
}
