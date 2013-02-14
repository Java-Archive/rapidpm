package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 16.10.12
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */
public class IssueStatusDAO extends GraphBaseDAO<IssueStatus> {
    private static final Logger logger = Logger.getLogger(IssueStatusDAO.class);

    public IssueStatusDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssueStatus.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssues(final IssueStatus status) {
        return super.getConnectedIssues(status);
    }

    public boolean delete(final IssueStatus entity, final IssueStatus assignTo){
        return super.deleteAttribute(entity, assignTo);
    }
}
