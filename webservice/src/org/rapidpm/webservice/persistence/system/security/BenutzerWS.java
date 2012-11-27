package org.rapidpm.webservice.persistence.system.security;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;

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
    private static final Logger logger = Logger.getLogger(BenutzerWS.class);

    // TODO hashed passwd, return SessionID
    @WebMethod(operationName = "authenticate")
    public boolean authenticate(@WebParam(name = "login") final String login,
                                @WebParam(name = "passwd") final String passwd) {
        final Subject user = SecurityUtils.getSubject();
        if (!user.isAuthenticated()) {
            final UsernamePasswordToken token = new UsernamePasswordToken(login, passwd);
            token.setRememberMe(true);
            try {
                user.login(token);
                logger.info("User [" + user.getPrincipal() + "] logged in successfully.");
            } catch (UnknownAccountException uae) {
                logger.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                logger.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                logger.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            } catch (AuthenticationException ae) {
                logger.info(ae.getLocalizedMessage());
            }
        }
        return user.isAuthenticated();
    }
}
