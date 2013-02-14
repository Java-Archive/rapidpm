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
 * Date: 05.11.12
 * Time: 10:16
 * To change this template use File | Settings | File Templates.
 */
public class IssueStoryPointDAO extends GraphBaseDAO<IssueStoryPoint> {
    private static final Logger logger = Logger.getLogger(IssueStatusDAO.class);

    public IssueStoryPointDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssueStoryPoint.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssues(final IssueStoryPoint storyPoint) {
        return super.getConnectedIssues(storyPoint);
    }

    public boolean delete(final IssueStoryPoint entity, final IssueStoryPoint assignTo){
        return super.deleteAttribute(entity, assignTo);
    }
}
