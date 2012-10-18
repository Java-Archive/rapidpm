package org.rapidpm.persistence;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Traversal;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Identifier;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 16.10.12
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public class GraphBaseDAO<T> {
    protected final GraphDatabaseService graphDb;
    protected final Index<Node> index_name;
    protected final Node root_node;
    protected final Node class_root_node;

    private final Class clazz;

    public GraphBaseDAO(final GraphDatabaseService graphDb, final Class clazz) {
        this.clazz = clazz;
        this.graphDb = graphDb;
        index_name = graphDb.index().forNodes(clazz.getSimpleName());
        this.root_node = graphDb.getNodeById(0);
        this.class_root_node = getClassRootNode();
    }

    private Node getClassRootNode() {
        TraversalDescription td = Traversal.description()
                .breadthFirst()
                .relationships(GraphRelationRegistry.getRootRelationshipTypeToClass(clazz), Direction.OUTGOING )
                .evaluator(Evaluators.atDepth(1));
        Traverser trav = td.traverse( root_node );
        Node node = null;
        for (Path path : trav) {
            node = path.endNode();
        }
        if (node == null)
            throw new NullPointerException("No class_root_node found. Please initialize graph database");

        return node;
    }

    public T persist(T entity) {
        if (entity == null)
            throw new NullPointerException(clazz.getSimpleName() + ": Object to persist can't be null");
        Long id = getIdFromEntity(entity);

        Transaction tx = graphDb.beginTx();
        try{
            Node node;
            final String nameVal = (String) clazz.getDeclaredField("NAME").get(entity);
            if (id == null || id == 0) {
                final Method method = clazz.getDeclaredMethod("get" + nameVal.substring(0,1).toUpperCase() + nameVal.substring(1));
                if (index_name.get(nameVal, method.invoke(entity)).getSingle() != null)
                    throw new IllegalArgumentException(clazz.getSimpleName() + ": Name already in use");
                node = graphDb.createNode();
                class_root_node.createRelationshipTo(node, GraphRelationRegistry.getAttributeChildRelationshipType());
                clazz.getDeclaredMethod("setId", Long.class).invoke(entity, node.getId());
            } else {
                node = graphDb.getNodeById(getIdFromEntity(entity));
            }
            setBasicProperties(node, entity, nameVal);
            tx.success();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            tx.finish();
        }
        return entity;
    }

    protected void setBasicProperties(Node node, Object entity, String nameAttribute){
        Field[] fieldNames = entity.getClass().getDeclaredFields();

        for (Field field : fieldNames) {
            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);
            if (field.isAnnotationPresent(Simple.class)) {
                try {
                    node.setProperty(field.getName(), field.get(entity) == null ? "null" : field.get(entity));
                } catch (IllegalAccessException e) { e.printStackTrace(); }
            }
            field.setAccessible(isAccessible);
        }
        index_name.remove(node, nameAttribute);
        index_name.add(node, nameAttribute, node.getProperty(nameAttribute));
    }

    public T getById(Long id) {
        return getObjectFromNode(graphDb.getNodeById(id));
    }

    public List<T> loadAllEntities() {
        TraversalDescription td = Traversal.description()
                .breadthFirst()
                .relationships(GraphRelationRegistry.getAttributeChildRelationshipType(), Direction.OUTGOING )
                .evaluator(Evaluators.atDepth(1));
        Traverser trav = td.traverse(class_root_node);
        List<T> entityList = new ArrayList<T>();
        for (Path path : trav) {
            entityList.add(getObjectFromNode(path.endNode()));
        }

        return entityList;
    }

    private T getObjectFromNode(Node node) {
        T entity = null;
        try {
            entity = (T) clazz.newInstance();

            Field[] fieldNames = entity.getClass().getDeclaredFields();
            Method method;
            for (Field field : fieldNames) {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if (field.isAnnotationPresent(Identifier.class)) {
                    field.set(entity, field.getType().cast(node.getId()));
                }

                if (field.isAnnotationPresent(Simple.class)) {
                    try {
                        field.set(entity, field.getType().cast(node.getProperty(field.getName())));
                    } catch (IllegalAccessException e) { e.printStackTrace(); }
                }

                field.setAccessible(isAccessible);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return entity;
    }


    private Long getIdFromEntity(T entity) {
        Long id = null;
        try {
            final Method method = clazz.getDeclaredMethod("getId");
            id = (Long) method.invoke(entity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return id;
    }
}


