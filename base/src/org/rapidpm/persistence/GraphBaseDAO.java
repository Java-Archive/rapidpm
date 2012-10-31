package org.rapidpm.persistence;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Traversal;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

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
    private static final Logger logger = Logger.getLogger(GraphBaseDAO.class);

    protected final GraphDatabaseService graphDb;
    protected final DaoFactory relDaoFactory;
    protected final Index<Node> index_name;
    protected final Node root_node;
    protected final Node class_root_node;

    private final Class clazz;

    public GraphBaseDAO(final GraphDatabaseService graphDb, final Class clazz, final DaoFactory relDaoFactory,
                        final Long projectId) {
        if (graphDb == null)
            throw new NullPointerException("GraphDb is null.");
        if (clazz == null)
            throw new NullPointerException("Class is null.");
        if (projectId == null)
            throw new NullPointerException("ProjectId is null.");
        if (projectId < 0)
            throw new NullPointerException("ProjectId must be positiv.");

//        if (relDaoFactory == null)
//            throw new NullPointerException("Rel. DaoFactory is null.");
        this.graphDb = graphDb;
        this.relDaoFactory = relDaoFactory;
        this.clazz = clazz;
        index_name = graphDb.index().forNodes(clazz.getSimpleName());
        this.root_node = graphDb.getNodeById(0);
        this.class_root_node = getClassRootNode(projectId);
    }

    public GraphBaseDAO(final GraphDatabaseService graphDb, final Class clazz, final DaoFactory relDaoFactory) {
        this(graphDb, clazz, relDaoFactory, 0L);
    }

    private Node getClassRootNode(final Long projectId) {
        boolean isProject = (projectId != 0) ;
        final TraversalDescription td = Traversal.description()
                .breadthFirst()
                .relationships(GraphRelationRegistry.getRootToClassRootRelType(clazz), Direction.OUTGOING)
                .evaluator(Evaluators.excludeStartPosition());

        if (isProject)  {
            td.evaluator(Evaluators.atDepth(2));
        } else {
            td.evaluator(Evaluators.atDepth(1));
        }
        final Traverser trav = td.traverse( root_node );
        Node node = null, project_root = null;
        for (final Path path : trav) {
            if (isProject) {
                if (project_root == null) project_root = path.endNode();
                if (projectId.equals(path.endNode().getProperty(GraphRelationRegistry.getRelationAttributeProjectId(), null)))
                    node = path.endNode();
            }
            else
                node = path.endNode();
        }
        if (node == null)
            if (isProject && project_root != null) {
                node = createNewProjectRootNode(project_root, projectId);
            } else
                throw new NullPointerException("No class_root_node found. Please initialize graph database");

        return node;
    }

    private Node createNewProjectRootNode(final Node project_root, final Long projectId) {
        final Transaction tx = graphDb.beginTx();
        Node project1 = null;
        try {
            project1 = graphDb.createNode();
            project1.setProperty(GraphRelationRegistry.getRelationAttributeProjectId(), projectId);
            project_root.createRelationshipTo(project1, GraphRelationRegistry.getRootToClassRootRelType(IssueBase.class));
            tx.success();
        } finally {
            tx.finish();
            if (project1 == null)
                throw new NullPointerException("Couldn't create new project root node");
            return project1;
        }

    }

    public T persist(final T entity) {
        if (entity == null)
            throw new NullPointerException(clazz.getSimpleName() + ": Object to persist can't be null");

        final Transaction tx = graphDb.beginTx();
        try{
            final Node node;
            final Method method = clazz.getDeclaredMethod("name");
            final String nameAtt = (String) method.invoke(entity);
            final Long id = getIdFromEntity(entity);
            if (id == null || id == 0) {
                if (index_name.get(method.getName(), nameAtt).getSingle() != null)
                    throw new IllegalArgumentException(clazz.getSimpleName() + ": Name already in use");
                node = graphDb.createNode();
                class_root_node.createRelationshipTo(node, GraphRelationRegistry.getClassRootToChildRelType());
                clazz.getDeclaredMethod("setId", Long.class).invoke(entity, node.getId());
            } else {
                node = graphDb.getNodeById(id);
            }
            setProperties(node, entity);
            index_name.remove(node, method.getName());
            index_name.add(node, method.getName(), nameAtt);

            tx.success();
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

    private void setProperties(final Node node, final T entity) {
        if (node == null)
            throw new NullPointerException(clazz.getSimpleName() + ": Node to persist to can't be null");
        if (entity == null)
            throw new NullPointerException(clazz.getSimpleName() + ": Object to persist can't be null");

        final Field[] fieldNames = entity.getClass().getDeclaredFields();

        for (final Field field : fieldNames) {
            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);

            if (field.isAnnotationPresent(Simple.class)) {
                try {
                    if (field.getAnnotation(Simple.class).clazz().equals("Date")) {
                        node.setProperty(field.getName(), field.get(entity) == null ? "0" : ((Date)field.get(entity)).getTime());
                    } else {
                        if (field.get(entity) != null)
                            node.setProperty(field.getName(), field.get(entity));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            else if (field.isAnnotationPresent(Relational.class)) {
                final Class aClass = field.getAnnotation(Relational.class).clazz();
                try {
                    if (field.getType().equals(List.class)) {
                        final DAO relDao = getRelationalDaoInstance(aClass);
                        final List entityList = (List)field.get(entity);
                        final Iterator it = entityList.iterator();
                        Long[] ids = new Long[entityList.size()];
                        int i = 0;
                        while (it.hasNext()) {
                            Object single = it.next();
                            if (relDao != null)
                                relDao.saveOrUpdate(single);
                            ids[i++] = getIdFromEntity(single, aClass);
                        }
                        node.setProperty(field.getName(), ids);
                    } else {
                        //method = field.getType().getDeclaredMethod("getId");
                        if (field.get(entity) != null)//(method != null && field.get(entity) != null) {
                                node.setProperty(field.getName(), getIdFromEntity(field.get(entity), aClass));
                        // }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            else if (field.isAnnotationPresent(Graph.class)) {
                try {
                    if (field.get(entity) != null) {
                        final Class aClass = field.getAnnotation(Graph.class).clazz();
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
    }

    private void connectSingleAttribute(Node startNode, Node endNode, Class aClass) {
        if (startNode == null)
            throw new NullPointerException("Startnode is null.");
        if (endNode == null)
            throw new NullPointerException("Endnode is null.");
        if (aClass == null)
            throw new NullPointerException("Class is null.");

        final RelationshipType relType = GraphRelationRegistry.getRelationshipTypeForClass(aClass);
        if (startNode.hasRelationship(relType, Direction.OUTGOING)) {
            for (Relationship rel : startNode.getRelationships(relType, Direction.OUTGOING))
                rel.delete();
        }
        connectAttribute(startNode, endNode, aClass);
    }

    private void connectAttribute(final Node startNode, final Node endNode, final Class aClass) {
        if (startNode == null)
            throw new NullPointerException("Startnode is null.");
        if (endNode == null)
            throw new NullPointerException("Endnode is null.");
        if (aClass == null)
            throw new NullPointerException("Class is null.");

        startNode.createRelationshipTo(endNode, GraphRelationRegistry.getRelationshipTypeForClass(aClass));
    }


    public List<T> loadAllEntities() {
        final TraversalDescription td = Traversal.description()
                .breadthFirst()
                .relationships(GraphRelationRegistry.getClassRootToChildRelType(), Direction.OUTGOING )
                .relationships(GraphRelationRegistry.getRelationshipTypeForClass(clazz), Direction.OUTGOING )
                .evaluator(Evaluators.excludeStartPosition());
        final Traverser trav = td.traverse(class_root_node);
        final List<T> entityList = new ArrayList<T>();
        for (final Node travNode : trav.nodes()) {
            entityList.add(getObjectFromNode(travNode));
        }

        return entityList;
    }

    public List<T> loadTopLevelEntities() {
        final TraversalDescription td = Traversal.description()
                .breadthFirst()
                .relationships(GraphRelationRegistry.getClassRootToChildRelType(), Direction.OUTGOING )
                .evaluator(Evaluators.excludeStartPosition());
        final Traverser trav = td.traverse(class_root_node);
        final List<T> entityList = new ArrayList<T>();
        for (final Node travNode : trav.nodes()) {
            entityList.add(getObjectFromNode(travNode));
        }

        return entityList;
    }

    public T findById(final Long id) {
        if (id == null)
            throw new NullPointerException("Id object is null.");

        return getObjectFromNode(graphDb.getNodeById(id));
    }

    public T findByEntity(final T entity) {
        if (entity == null)
            throw new NullPointerException("Entity object is null.");

        return findById(getIdFromEntity(entity));
    }

    protected T getObjectFromNode(final Node node) {
        return this.<T>getObjectFromNode(node , clazz);
    }

    protected <E> E getObjectFromNode(final Node node, final Class clazz) {
        if (node == null)
            throw new NullPointerException("Node is null.");
        if (clazz == null)
            throw new NullPointerException("Class is null.");

        E entity = null;
        try {
            try {
                entity = (E) clazz.getConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                try {
                    entity = (E) clazz.getConstructor(Long.class).newInstance(-1L);
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            final Field[] fieldNames = entity.getClass().getDeclaredFields();
            for (final Field field : fieldNames) {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if (field.isAnnotationPresent(Identifier.class)) {
                    field.set(entity, field.getType().cast(node.getId()));
                }

                if (field.isAnnotationPresent(Simple.class)) {
                    if (field.getAnnotation(Simple.class).clazz().equals("Date")) {
                        field.set(entity, new Date(Long.class.cast(node.getProperty(field.getName()))));

                    } else
                        field.set(entity, field.getType().cast(node.getProperty(field.getName(), null)));
                }

                else if (field.isAnnotationPresent(Relational.class)) {
                    //Load via relational DAO's
                    final DAO relDao = getRelationalDaoInstance(field.getAnnotation(Relational.class).clazz());
                    if (relDao != null) {
                        if (field.getType().equals(List.class)) {
                            final long[] entityList = (long[])node.getProperty(field.getName(), new long[]{});
                            final List<Object> ids = new ArrayList<>();
                            Object obj = null;
                            for (Object single : entityList) {
                                obj = relDao.findByID((Long)single);
                                if (obj != null)
                                    ids.add(obj);
                                else System.out.println("Requested object not in rel databse: " + field.getAnnotation
                                        (Relational.class).clazz().getSimpleName() + " " + (Long)single);
                            }
                            field.set(entity, ids);
                        } else {
                            if (node.getProperty(field.getName(), null) != null)
                                field.set(entity, relDao.findByID(Long.class.cast(node.getProperty(field.getName()))));
                        }
                    }
                }
                else if (field.isAnnotationPresent(Graph.class)) {
                    final Class aClass = field.getAnnotation(Graph.class).clazz();
                    Node travNode = null;
                    for (Relationship rel : node.getRelationships(GraphRelationRegistry.getRelationshipTypeForClass(aClass),
                            Direction.OUTGOING))
                        travNode = rel.getOtherNode(node);

                    field.set(entity, getObjectFromNode(travNode, aClass));
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

    protected Long getIdFromEntity(final Object entity) {
        return getIdFromEntity(entity, clazz);
    }


    protected Long getIdFromEntity(final Object entity, final Class aClass) {
        if (entity == null)
            throw new NullPointerException("Can't get Id from null.");
        if (aClass == null)
            throw new NullPointerException("Class to get Id from can't be null.");

        Long id = null;
        try {
            final Method method = aClass.getDeclaredMethod("getId");
            if (method == null)
                throw new NullPointerException("No method 'getId' in Class" + aClass.getSimpleName() + "found.");
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

    private DAO getRelationalDaoInstance(final Class aClass) {
        DAO relDao = null;
        if (relDaoFactory != null) {
            final Method method;
            try {
                method = relDaoFactory.getClass().getDeclaredMethod("get" + aClass.getSimpleName() + "DAO");
                relDao = (DAO)method.invoke(relDaoFactory);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return relDao;
    }


    public boolean delete(T entity) {
        if (entity == null)
            throw new NullPointerException("Object to delete can't be null.");

        boolean success = false;
        final Long id = getIdFromEntity(entity);
        final Transaction tx = graphDb.beginTx();
        try{
            Node node;
            if (id != null && id != 0) {
                node = graphDb.getNodeById(id);
                final RelationshipType relType = GraphRelationRegistry.getSubIssueRelationshipType();
                for (Relationship rel : node.getRelationships()) {
                    if (rel.isType(relType) && rel.getStartNode().equals(node)) {
                        for (Relationship parent : node.getRelationships(relType, Direction.INCOMING)) {
                            parent.getStartNode().createRelationshipTo(rel.getEndNode(),relType);
                        }
                    }
                    rel.delete();
                }
                node.delete();
            }
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }
}


