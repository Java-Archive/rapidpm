package org.rapidpm.persistence;


import org.apache.log4j.Logger;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 12:58
 * To change this template use File | Settings | File Templates.
 */
public class GraphRelationRegistry {
    private static final Logger logger = Logger.getLogger(GraphRelationRegistry.class);

    public static String getRelationAttributeName() {
        return "name";
    }

    public static String getRelationAttributeProjectId() {
        return "projectid";
    }

    public static RelationshipType getClassRootToChildRelType() {
        return DynamicRelationshipType.withName("value");
    }

    public static RelationshipType getRootToClassRootRelType(final Class clazz) {
        return DynamicRelationshipType.withName("root_" + clazz.getSimpleName());
    }

    public static RelationshipType getRelationshipTypeForClass(final Class clazz) {
        return DynamicRelationshipType.withName(clazz.getSimpleName());
    }

    public static RelationshipType getSubIssueRelationshipType() {
        return DynamicRelationshipType.withName(IssueBase.class.getSimpleName());
    }
}
