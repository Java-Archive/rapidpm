package org.rapidpm.security.realm;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.persistence.system.security.berechtigungen.Berechtigung;
import org.rapidpm.persistence.system.security.berechtigungen.Rolle;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Alexander Vos
 * Date: 27.11.12
 * Time: 11:45
 */
public class DaoRealm extends AuthorizingRealm {
    private static final Logger logger = Logger.getLogger(DaoRealm.class);

    // TODO CDI @Inject
    private final BenutzerDAO benutzerDAO = DaoFactorySingleton.getInstance().getBenutzerDAO();

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
        final UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        final String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        final Benutzer benutzer = benutzerDAO.loadBenutzerForLogin(username);
        if (benutzer == null) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }
        final String password = benutzer.getPasswd();
        final SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());
//        final String salt = benutzer.getPasswdSalt(); // TODO get salt
//        info.setCredentialsSalt(ByteSource.Util.bytes(salt));
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        final String username = (String) getAvailablePrincipal(principals);
        final Set<String> roles = new HashSet<>();
        final Set<String> permissions = new HashSet<>();
        final Benutzer benutzer = benutzerDAO.loadBenutzerForLogin(username);
        if (benutzer == null) {
            throw new AuthorizationException("No account found for user [" + username + "]");
        }
        final Set<Rolle> rollen = benutzer.getRollen();
        for (final Rolle rolle : rollen) {
            roles.add(rolle.getName());
            for (final Berechtigung berechtigung : rolle.getBerechtigungen()) {
                permissions.add(berechtigung.getName());
            }
        }
        final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setStringPermissions(permissions);
        return info;
    }
}
