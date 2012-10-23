package org.rapidpm.persistence;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Traversal;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.*;
import org.rapidpm.persistence.system.security.Benutzer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 16.10.12
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public class GraphBaseDAO<T> {

    protected final GraphDatabaseService graphDb;
    protected final DaoFactory relDaoFactory;
    protected final Index<Node> index_name;
    protected final Node root_node;
    protected final Node class_root_node;

    private final Class clazz;

    public GraphBaseDAO(final GraphDatabaseService graphDb, final Class clazz, final DaoFactory relDaoFactory) {
        if (graphDb == null)
            throw new NullPointerException("GraphDb is null.");
        if (clazz == null)
            throw new NullPointerException("Class is null.");
//        if (relDaoFactory == null)
//            throw new NullPointerException("Rel. DaoFactory is null.");

        this.clazz = clazz;
        this.graphDb = graphDb;
        this.relDaoFactory = relDaoFactory;
        index_name = graphDb.index().forNodes(clazz.getSimpleName());
        this.root_node = graphDb.getNodeById(0);
        this.class_root_node = getClassRootNode();
    }

    private Node getClassRootNode() {
        TraversalDescription td = Traversal.description()
                .breadthFirst()
                .relationships(GraphRelationRegistry.getRootToClassRootRelType(clazz), Direction.OUTGOING )
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
                class_root_node.createRelationshipTo(node, GraphRelationRegistry.getClassRootToChildRelType());
                clazz.getDeclaredMethod("setId", Long.class).invoke(entity, node.getId());
            } else {
                node = graphDb.getNodeById(id);
            }
            setProperties(node, entity, nameVal);
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

    private void setProperties(Node node, T entity, String nameAttribute) {
        if (node == null)
            throw new NullPointerException(clazz.getSimpleName() + ": Node to persist to can't be null");
        if (entity == null)
            throw new NullPointerException(clazz.getSimpleName() + ": Object to persist can't be null");
        if (nameAttribute == null)
            throw new NullPointerException(clazz.getSimpleName() + ": Name object can't be null");


        Field[] fieldNames = entity.getClass().getDeclaredFields();

        for (Field field : fieldNames) {
            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);
            if (field.isAnnotationPresent(Simple.class)) {
                try {
                    if (field.getAnnotation(Simple.class).clazz().equals("Date")) {
                        node.setProperty(field.getName(), field.get(entity) == null ? "0" : ((Date)field.get(entity)).getTime());
                    } else
                        node.setProperty(field.getName(), field.get(entity) == null ? "null" : field.get(entity));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            else if (field.isAnnotationPresent(Relational.class)) {
                Class aClass = field.getAnnotation(Relational.class).clazz();

                try {
                    if (field.getType().equals(List.class)) {
                        if (relDaoFactory != null) {
                            Method method = relDaoFactory.getClass().getDeclaredMethod("get" + aClass.getSimpleName() + "DAO");
                            final DAO relDao = (DAO)method.invoke(relDaoFactory);
                            if (relDao != null) {
                                List entityList = (List)field.get(entity);
                                Iterator it = entityList.iterator();
                                Long[] ids = new Long[entityList.size()];
                                int i = 0;
                                while (it.hasNext()) {
                                    Object single = it.next();
                                    relDao.saveOrUpdate(single);
                                    ids[i++] = getIdFromEntity(relDao.getEntityManager().find(aClass, single),
                                        aClass);
                                }
                                node.setProperty(field.getName(), ids);
                            }
                        }
                    } else {
                        Method method = field.getType().getDeclaredMethod("getId");
                        if (method != null && field.get(entity) != null) {
                                node.setProperty(field.getName(), method.invoke(field.get(entity)));
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvocationTargetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            else if (field.isAnnotationPresent(Graph.class)) {
                try {
                    final Class aClass = field.getAnnotation(Graph.class).clazz();

                    if (field.get(entity) != null) {
                        Long id = getIdFromEntity(field.get(entity), aClass);
                        if (id != null && id != 0) {
                            connectSingleAttribute(node, graphDb.getNodeById(id), aClass);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            field.setAccessible(isAccessible);
        }
        index_name.remove(node, nameAttribute);
        index_name.add(node, nameAttribute, node.getProperty(nameAttribute));
    }

    private void connectSingleAttribute(Node startNode, Node endNode, Class aClass) {
        if (startNode == null)
            throw new NullPointerException("Startnode is null.");
        if (endNode == null)
            throw new NullPointerException("Endnode is null.");
        if (aClass == null)
            throw new NullPointerException("Class is null.");

        if (startNode.hasRelationship(GraphRelationRegistry.getRelationshipTypeForClass
                (aClass), Direction.OUTGOING)) {
            for (Relationship rel : startNode.getRelationships(GraphRelationRegistry.getRelationshipTypeForClass
                    (aClass), Direction.OUTGOING))
                rel.delete();
        }
        connectAttribute(startNode, endNode, aClass);
    }

    private void connectAttribute(Node startNode, Node endNode, Class aClass) {
        if (startNode == null)
            throw new NullPointerException("Startnode is null.");
        if (endNode == null)
            throw new NullPointerException("Endnode is null.");
        if (aClass == null)
            throw new NullPointerException("Class is null.");

        startNode.createRelationshipTo(endNode, GraphRelationRegistry.getRelationshipTypeForClass
                (aClass));
    }


    public List<T> loadAllEntities() {
        TraversalDescription td = Traversal.description()
                .breadthFirst()
                .relationships(GraphRelationRegistry.getClassRootToChildRelType(), Direction.OUTGOING )
                .relationships(GraphRelationRegistry.getRelationshipTypeForClass(clazz), Direction.OUTGOING )
                .evaluator(Evaluators.excludeStartPosition());
        Traverser trav = td.traverse(class_root_node);
        List<T> entityList = new ArrayList<T>();
        for (Node travNode : trav.nodes()) {
            entityList.add(getObjectFromNode(travNode, clazz));
        }

        return entityList;
    }

    public List<T> loadTopLevelEntities() {
        TraversalDescription td = Traversal.description()
                .breadthFirst()
                .relationships(GraphRelationRegistry.getClassRootToChildRelType(), Direction.OUTGOING )
                .evaluator(Evaluators.excludeStartPosition());
        Traverser trav = td.traverse(class_root_node);
        List<T> entityList = new ArrayList<T>();
        for (Node travNode : trav.nodes()) {
            entityList.add(getObjectFromNode(travNode, clazz));
        }

        return entityList;
    }

    public boolean delete(T entity) {
        if (entity == null)
            throw new NullPointerException("Object to delete can't be null.");

        Long id = getIdFromEntity(entity);
        Transaction tx = graphDb.beginTx();
        try{
            Node node;
            if (id == null || id == 0)
                return true;
            else {
                node = graphDb.getNodeById(id);
                for (Relationship rel : node.getRelationships())
                    rel.delete();
                node.delete();
            }
            tx.success();
        } finally {
            tx.finish();
            return true;
        }

    }

    public T getById(Long id) {
        if (id == null)
            throw new NullPointerException("Id object is null.");

        return getObjectFromNode(graphDb.getNodeById(id), clazz);
    }

    public T getByEntity(T entity) {
        if (entity == null)
            throw new NullPointerException("Entity object is null.");

        return getById(getIdFromEntity(entity));
    }

    protected T getObjectFromNode(Node node, Class clazz) {
        if (node == null)
            throw new NullPointerException("Node is null.");
        if (clazz == null)
            throw new NullPointerException("Class is null.");

        T entity = null;
        try {
            entity = (T) clazz.newInstance();

            Field[] fieldNames = entity.getClass().getDeclaredFields();
            for (Field field : fieldNames) {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if (field.isAnnotationPresent(Identifier.class)) {
                    field.set(entity, field.getType().cast(node.getId()));
                }

                if (field.isAnnotationPresent(Simple.class)) {
                    try {
                        if (field.getAnnotation(Simple.class).clazz().equals("Date")) {
                            field.set(entity, new Date(Long.class.cast(node.getProperty(field.getName()))));

                        } else
                            field.set(entity, field.getType().cast(node.getProperty(field.getName())));
                    } catch (IllegalAccessException e) { e.printStackTrace(); }
                }

                else if (field.isAnnotationPresent(Relational.class)) {
                    //Load via relational DAO's
                    final Class aClass = field.getAnnotation(Relational.class).clazz();
                    Method method = relDaoFactory.getClass().getDeclaredMethod("get" + aClass.getSimpleName() + "DAO");
                    final DAO relDao = (DAO)method.invoke(relDaoFactory);
                    if (relDao != null) {
                        if (field.getType().equals(List.class)) {
                            long[] entityList = (long[])node.getProperty(field.getName(), new long[]{});
                            List<Object> ids = new ArrayList<>();
                            for (Object single : entityList) {

                                Long id = (Long)single;
                                ids.add(relDao.findByID(id));
                            }
                            field.set(entity, ids);
                        } else {
                            if (node.getProperty(field.getName(), null) != null)
                                field.set(entity, relDao.findByID(Long.class.cast(node.getProperty(field.getName()))));
                        }
                    }
                }
                else if (field.isAnnotationPresent(Graph.class)) {
                    try {
                        final Class aClass = field.getAnnotation(Graph.class).clazz();
                        Node travNode = null;
                        for (Relationship rel : node.getRelationships(GraphRelationRegistry.getRelationshipTypeForClass(aClass),
                                Direction.OUTGOING))
                            travNode = rel.getOtherNode(node);

                        field.set(entity, getObjectFromNode(travNode, aClass));
//                            }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                field.setAccessible(isAccessible);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return entity;
    }

    protected Long getIdFromEntity(Object entity) {
        return getIdFromEntity(entity, clazz);
    }


    protected Long getIdFromEntity(Object entity, Class aClass) {
        if (entity == null)
            throw new NullPointerException("Can't get Id from null.");
        if (aClass == null)
            throw new NullPointerException("Class to get Id from can't be null.");

        Long id = null;
        try {
            final Method method = aClass.getDeclaredMethod("getId");
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


