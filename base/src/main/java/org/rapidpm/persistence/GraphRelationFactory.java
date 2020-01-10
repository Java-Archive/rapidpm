package org.rapidpm.persistence;


import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 18.10.12
 * Time: 12:58
 * To change this template use File | Settings | File Templates.
 */
public abstract class GraphRelationFactory {

    public static String getRelationAttributeName() {
        return "name";
    }

    public static String getRelationAttributeProjectId() {
        return "projectid";
    }

    public static String getRelationAttributeProjectToken() {
        return "projecttoken";
    }

    public static String getRelationAttributeTokenId() {
        return "projecttokenid";
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
