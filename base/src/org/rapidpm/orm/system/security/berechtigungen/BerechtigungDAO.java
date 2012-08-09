package org.rapidpm.orm.system.security.berechtigungen;

import org.rapidpm.orm.BaseDaoFactory;
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
 * @since 19.09.2010
 *        Time: 18:01:24
 */

public class BerechtigungDAO extends BaseDaoFactory.BaseDAO<Long, Berechtigung> {
    private static final Logger logger = Logger.getLogger(BerechtigungDAO.class);

    public BerechtigungDAO(final EntityManager entityManager) {
        super(entityManager, Berechtigung.class);
    }

    public Berechtigung loadBerechtigung(final String name) {
        if (logger.isInfoEnabled()) {
            logger.info("loadBerechtigung : " + name);
        }
        final TypedQuery<Berechtigung> query = entityManager.createQuery("from Berechtigung  b where b.name=:name", Berechtigung.class).setParameter(
                "name",
                name);
        return getSingleResultOrNull(query);
        //        return createWhereClause().eq("name", name).findUnique();

        //        final ObjectSet<Berechtigung> berechtigungObjectSet = entityManager.query(new Predicate<Berechtigung>() {
        //            @Override
        //            public boolean match(final Berechtigung berechtigung) {
        //                return berechtigung.getName().equals(name);
        //            }
        //        });
        //
        //        if (berechtigungObjectSet.size() == 1) {
        //            return berechtigungObjectSet.get(0);
        //        } else {
        //            return null;
        //        }
    }


}