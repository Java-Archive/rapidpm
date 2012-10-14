package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

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
 * @since 31.08.2010
 *        Time: 13:00:32
 */

public class IssueBaseDAO extends DAO<Long, IssueBase> {
    private static final Logger logger = Logger.getLogger(IssueBaseDAO.class);

    //    private SimpleDateFormat format = new SimpleDateFormat(Constants.YYYY_MM_DD, Locale.GERMAN);

    public IssueBaseDAO(final EntityManager entityManager) {
        super(entityManager, IssueBase.class);
    }

    public IssueBase loadIssueFor(final long issueNr) {
        final TypedQuery<IssueBase> typedQuery = entityManager.createQuery("from IssueBase i where i.id=:issueNr", IssueBase.class).setParameter("issueNr", issueNr);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("id", issueNr).findUnique();
    }
}
