package org.rapidpm.orm.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.orm.BaseDAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 11/23/10
 * Time: 11:31 AM
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
public class IssuePriorityDAO extends BaseDAO<Long, IssuePriority> {
    public IssuePriorityDAO(final EntityManager entityManager) {
        super(entityManager, IssuePriority.class);
    }

    public IssuePriority loadPriority(final String priority) {
        final TypedQuery<IssuePriority> typedQuery = entityManager.createQuery("from IssuePriority  p where p.name=:priority", IssuePriority.class).setParameter(
                "priority",
                priority);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("name", priority).findUnique();
    }

    public IssuePriority loadPriorityDringend_u_Wichtig() {
        return loadPriority("dringend und wichtig");
        //        return createWhereClause().eq("name", "dringend und wichtig").findUnique();
    }


}
