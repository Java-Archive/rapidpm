package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.neo4j.graphdb.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 17.10.12
 * Time: 13:09
 * To change this template use File | Settings | File Templates.
 */
public class IssueTypeDAO extends GraphBaseDAO<IssueType> {

    public IssueTypeDAO(final GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssueType.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssues(final IssueType type) {
        return super.getConnectedIssues(type);
    }

    public boolean delete(final IssueType entity, final IssueType assignTo){
        return super.deleteAttribute(entity, assignTo);
    }
}
