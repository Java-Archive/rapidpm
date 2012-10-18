package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;


import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 12.10.12
 * Time: 08:27
 * To change this template use File | Settings | File Templates.
 */
public class IssueTypeDAO extends DAO<Long, IssueType> {

    public IssueTypeDAO(final EntityManager entityManager) {
        super(entityManager, IssueType.class);
    }

    public IssueType loadType(final String type) {
        final TypedQuery<IssueType> typedQuery = entityManager.createQuery("from IssueType t where t.name=:type", IssueType.class).setParameter("type",
                type);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("name", status).findUnique();
    }

    public IssueType loadTypeBug() {
        return loadType("bug");
        //        return createWhereClause().eq("name", "dringend und wichtig").findUnique();
    }
}
