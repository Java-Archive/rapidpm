package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 11/23/10
 * Time: 11:59 AM
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
public class IssueStatusDAO extends DAO<Long, IssueStatus> {

    public IssueStatusDAO(final EntityManager entityManager) {
        super(entityManager, IssueStatus.class);
    }

    public IssueStatus loadStatus(final String status) {
        final TypedQuery<IssueStatus> typedQuery = entityManager.createQuery("from IssueStatus s where s.name=:status", IssueStatus.class).setParameter("status",
                status);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("name", status).findUnique();
    }

    public IssueStatus loadStatusOpen() {
        return loadStatus("open");
        //        return createWhereClause().eq("name", "open").findUnique();
    }

    public List<String> loadAllAsString() {
        final List<String> statusList = new ArrayList<>();
        final List<IssueStatus> issueStatusListe = this.loadAllEntities();
        for (final IssueStatus issueStatusObj : issueStatusListe) {
            statusList.add(issueStatusObj.getStatusName());
        }
        return statusList;
    }
}
