package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

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
public class IssueVersionDAO extends GraphBaseDAO<IssueVersion> {

    public IssueVersionDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssueVersion.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssues(final IssueVersion version) {
        return super.getConnectedIssues(version);
    }

    public boolean delete(final IssueVersion entity, final IssueVersion assignTo){
        return super.deleteAttribute(entity, assignTo);
    }
}
