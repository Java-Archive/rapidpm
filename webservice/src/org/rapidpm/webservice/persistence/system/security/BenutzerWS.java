package org.rapidpm.webservice.persistence.system.security;

import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.system.security.BenutzerDAO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * User: Alexander Vos
 * Date: 22.11.12
 * Time: 13:59
 */
@WebService(serviceName = "BenutzerWS")
public class BenutzerWS {

    // TODO CDI @Inject
    private BenutzerDAO benutzerDAO = DaoFactorySingelton.getInstance().getBenutzerDAO();

    // TODO hashed passwd, return Benutzer/Session
    @WebMethod(operationName = "authenticate")
    public boolean authenticate(@WebParam(name = "login") final String login,
                                @WebParam(name = "passwd") final String passwd) {
        return benutzerDAO.authenticate(login, passwd) != null;
    }
}
