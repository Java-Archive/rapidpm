package org.rapidpm.webservice.persistence.system.security;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webservice.mapping.FlatBaseWS;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * User: Alexander Vos
 * Date: 22.11.12
 * Time: 13:59
 */
@WebService(serviceName = "BenutzerWS")
public class BenutzerWS extends FlatBaseWS<Benutzer, BenutzerDAO, FlatBenutzer> {
    private static final Logger logger = Logger.getLogger(BenutzerWS.class);

    public BenutzerWS() {
        super(DaoFactorySingelton.getInstance().getBenutzerDAO());
    }

    @Override
    protected FlatBenutzer toFlatObject(final Benutzer benutzer) {
        if (benutzer == null) {
            return null;
        }
        final FlatBenutzer flatBenutzer = new FlatBenutzer();
        flatBenutzer.setId(benutzer.getId());
        flatBenutzer.setLogin(benutzer.getLogin());
        flatBenutzer.setEmail(benutzer.getEmail());
        return flatBenutzer;
    }

    // TODO hashed passwd, return SessionID
    @WebMethod
    public FlatBenutzer authenticate(@WebParam(name = "login") final String login,
                                     @WebParam(name = "passwd") final String passwd) throws AuthenticationException {
        final Subject user = SecurityUtils.getSubject();
        final Session session = user.getSession(); // new session with every request!?
        if (!user.isAuthenticated()) {
            final UsernamePasswordToken token = new UsernamePasswordToken(login, passwd);
            token.setRememberMe(true);
            user.login(token); // throws AuthenticationException on failure
        }
        // Authentication successful
        // TODO get Benutzer from Shiro?
        final Benutzer benutzer = dao.authenticate(login, passwd);
        return toFlatObject(benutzer);
    }

    @WebMethod
    public void logout() {
        final Subject user = SecurityUtils.getSubject();
        user.logout();
    }
}
