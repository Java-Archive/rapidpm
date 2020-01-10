package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.neo4j.graphdb.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 18.10.12
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class IssueComponentDAO extends GraphBaseDAO<IssueComponent> {

    public IssueComponentDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssueComponent.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssues(final IssueComponent component) {
        return super.getConnectedIssues(component);
    }

    public boolean delete(final IssueComponent entity){
        return super.deleteSimpleAttribute(entity);
    }

//    public boolean delete(final IssueComponent entity, final IssueComponent assignTo){
//        return super.deleteAttribute(entity, assignTo);
//    }
}
