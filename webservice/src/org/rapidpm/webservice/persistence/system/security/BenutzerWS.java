package org.rapidpm.webservice.persistence.system.security;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
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
 * User: Alexander Vos
 * Date: 22.11.12
 * Time: 13:59
 */
@WebService(serviceName = "BenutzerWS")
public class BenutzerWS extends FlatBaseWS<Benutzer, BenutzerDAO, FlatBenutzer> {
    private static final Logger logger = Logger.getLogger(BenutzerWS.class);

    public BenutzerWS() {
        super(Benutzer.class, FlatBenutzer.class);
    }

    @Override
    protected BenutzerDAO getDao() {
        return daoFactory.getBenutzerDAO();
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
//            try {
                user.login(token); // throws AuthenticationException on failure
//            } catch (AuthenticationException e) {
//                throw new AuthenticationException("Login failed");
//            }
        }
        // Authentication successful
        // TODO get Benutzer from Shiro?
        final Benutzer benutzer = dao.authenticate(login, passwd);
        return toFlatEntity(benutzer);
    }

    @WebMethod
    public void logout() {
        final Subject user = SecurityUtils.getSubject();
        user.logout();
    }
}
