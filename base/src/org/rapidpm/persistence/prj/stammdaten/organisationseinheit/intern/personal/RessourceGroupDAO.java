package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.BaseDAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 12.09.12
 * Time: 13:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RessourceGroupDAO extends BaseDAO<Long, RessourceGroup> {
    private static final Logger logger = Logger.getLogger(RessourceGroupDAO.class);

    public RessourceGroupDAO(final EntityManager entityManager) {
        super(entityManager, RessourceGroup.class);
    }

    public RessourceGroup loadRessourceGroupByName(final String name) {
        final TypedQuery<RessourceGroup> typedQuery = entityManager.createQuery("from RessourceGroup rg "
                + "where rg.name=:name ", RessourceGroup.class).setParameter("name", name);
        final RessourceGroup singleResultOrNull = getSingleResultOrNull(typedQuery);
        return singleResultOrNull;
    }









    public RessourceGroup loadRessourceGroup(final String login, final String passwd, final Long webappOID) {
        RessourceGroup benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen RessourceGroup");
            benutzer = loadAnonymousRessourceGroup(webappOID);
        } else {
            final TypedQuery<RessourceGroup> typedQuery1 = entityManager.createQuery("from RessourceGroup b where b.login=:login and b.hidden=false and b.active=true and b.passwd=:passwd " + "and b.benutzerWebapplikation.id=:webappOID", RessourceGroup.class);
            final TypedQuery<RessourceGroup> typedQuery = typedQuery1.setParameter("login", login).setParameter("passwd", passwd).setParameter("webappOID", webappOID);

            benutzer = getSingleResultOrNull(typedQuery);
            if (benutzer == null) {
                benutzer = loadAnonymousRessourceGroup(webappOID);
}
