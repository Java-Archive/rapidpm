package org.rapidpm.persistence.system.security;

import org.rapidpm.persistence.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 23.07.2010
 *        Time: 20:59:59
 */

public class BenutzerWebapplikationDAO extends BaseDaoFactory.BaseDAO<Long, BenutzerWebapplikation> {
    private static final Logger logger = Logger.getLogger(BenutzerWebapplikationDAO.class);

    public BenutzerWebapplikationDAO(final EntityManager entityManager) {
        super(entityManager, BenutzerWebapplikation.class);
    }

    //    public List<BenutzerWebapplikation> loadAllEntities() {
    //        return loadAllEntities(BenutzerWebapplikation.class);
    //    }

    public BenutzerWebapplikation loadBenutzerWebapplikation(final String name) {
        final TypedQuery<BenutzerWebapplikation> typedQuery = entityManager.createQuery("from BenutzerWebapplikation  bw where bw.webappName=:name",
                BenutzerWebapplikation.class).setParameter("name", name);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("webapp_name", name).findUnique();
        //        BenutzerWebapplikation webapplikation = null;
        //        final ObjectSet<BenutzerWebapplikation> objSet = entityManager.query(new Predicate<BenutzerWebapplikation>() {
        //            @Override
        //            public boolean match(final BenutzerWebapplikation webapplikation) {
        //                return webapplikation.getWebappName().equals(name);
        //            }
        //        });
        //        final int size = objSet.size();
        //        if (size == 1) {
        //            webapplikation = objSet.get(0);
        //        } else {
        //            webapplikation = null;
        //        }
        //        return webapplikation;
    }


}