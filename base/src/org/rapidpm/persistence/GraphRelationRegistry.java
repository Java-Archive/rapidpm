package org.rapidpm.persistence;


import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 12:58
 * To change this template use File | Settings | File Templates.
 */
public class GraphRelationRegistry {

    public static String getRelationAttributeName() {
        return "name";
    }

    public static RelationshipType getAttributeChildRelationshipType() {
        return DynamicRelationshipType.withName("value");
    }

    public static RelationshipType getRootRelationshipTypeToClass(Class clazz) {
        return DynamicRelationshipType.withName("root_" + clazz.getSimpleName());
    }
}
