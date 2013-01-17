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
 * Date: 17.10.12
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class IssuePriorityDAO extends GraphBaseDAO<IssuePriority> {
    private static final Logger logger = Logger.getLogger(IssuePriorityDAO.class);

    public IssuePriorityDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssuePriority.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssues(final IssuePriority priority) {
        return super.getConnectedIssues(priority);
    }

    public boolean delete(final IssuePriority entity, final IssuePriority assignTo){
        return super.deleteAttribute(entity, assignTo);
    }
}
