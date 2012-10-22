package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;


import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.GraphRelationRegistry;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Graph;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Relational;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 09:54
 * To change this template use File | Settings | File Templates.
 */

public class IssueBaseDAO extends GraphBaseDAO<IssueBase> {

    public IssueBaseDAO(GraphDatabaseService graphDb) {
        super(graphDb, IssueBase.class);
    }
}
