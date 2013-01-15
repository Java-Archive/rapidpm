package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Traversal;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.GraphRelationRegistry;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 18.10.12
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class IssueComponentDAO extends GraphBaseDAO<IssueComponent> {
    private static final Logger logger = Logger.getLogger(IssueComponentDAO.class);

    public IssueComponentDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssueComponent.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssuesFromProject(final IssueComponent component, final Long projectId) {
        return super.getConnectedIssuesFromProject(component, projectId);
    }

    public boolean delete(final IssueComponent entity){
        return super.deleteSimpleAttribute(entity);
    }

//    public boolean delete(final IssueComponent entity, final IssueComponent assignTo){
//        return super.deleteAttribute(entity, assignTo);
//    }
}
