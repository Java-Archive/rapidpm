package org.rapidpm.persistence;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Traversal;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 16.10.12
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public class GraphBaseDAO<T> {
    private static final Logger logger = Logger.getLogger(GraphBaseDAO.class);

    protected final GraphDatabaseService graphDb;
    private final DaoFactory daoFactory;
    private final Index<Node> index_name;
    private final Node root_node;
    protected final Node class_root_node;

    private final Class clazz;

    public GraphBaseDAO(final GraphDatabaseService graphDb, final Class clazz, final DaoFactory daoFactory,
                        final Long projectId) {
        if (graphDb == null)
            throw new NullPointerException("GraphDb is null.");
        if (clazz == null)
            throw new NullPointerException("Class is null.");
        if (projectId == null)
            throw new NullPointerException("ProjectId is null.");
        if (projectId < 0)
            throw new IllegalArgumentException("ProjectId must be positiv");
        if (daoFactory == null)
            throw new NullPointerException("Rel. DaoFactory is null.");
        this.graphDb = graphDb;
        this.daoFactory = daoFactory;
        this.clazz = clazz;
        index_name = graphDb.index().forNodes(clazz.getSimpleName());
        this.root_node = graphDb.getNodeById(0);
        this.class_root_node = getClassRootNode(projectId);
    }

    public GraphBaseDAO(final GraphDatabaseService graphDb, final Class clazz, final DaoFactory daoFactory) {
        this(graphDb, clazz, daoFactory, 0L);
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
        Node node = null;
        Node project_root = null;
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
        Node newProjectNode = null;
        try {
            final DAO projectDao = getDaoInstance(PlannedProject.class);
            PlannedProject project = (PlannedProject) projectDao.findByID(projectId);
            newProjectNode = graphDb.createNode();
            newProjectNode.setProperty(GraphRelationRegistry.getRelationAttributeProjectId(), projectId);
            newProjectNode.setProperty(GraphRelationRegistry.getRelationAttributeProjectToken(), project.getProjektToken());
            newProjectNode.setProperty(GraphRelationRegistry.getRelationAttributeTokenId(), 1);
            project_root.createRelationshipTo(newProjectNode, GraphRelationRegistry.getRootToClassRootRelType(IssueBase.class));
            tx.success();
        } finally {
            tx.finish();
            if (newProjectNode == null)
                throw new NullPointerException("Couldn't create new project root node");
            return newProjectNode;
        }

    }

    public T persist(final T entity) throws IllegalArgumentException{
        if (entity == null)
            throw new NullPointerException(clazz.getSimpleName() + ": Object to persist can't be null");

        if (logger.isDebugEnabled())
            logger.debug("persist: " + entity);

        final Transaction tx = graphDb.beginTx();
        try{
            final Node node;
            final Method method = clazz.getDeclaredMethod("name");
            final String nameAtt = ((String) method.invoke(entity)).toLowerCase();
            final Long id = getIdFromEntity(entity);
            final Node indexNode = index_name.get(method.getName(), nameAtt).getSingle();
            if (indexNode != null) {
                if (id == null || (indexNode.getId() != id))
                    throw new IllegalArgumentException(clazz.getSimpleName() + ": Name already in use");
            }
            if (id == null || id == 0) {
                node = graphDb.createNode();
                class_root_node.createRelationshipTo(node, GraphRelationRegistry.getClassRootToChildRelType());
                final Method setIdMethod = clazz.getDeclaredMethod("setId", Long.class);
                boolean isAccessible = setIdMethod.isAccessible();
                setIdMethod.setAccessible(true);
                setIdMethod.invoke(entity, node.getId());
                setIdMethod.setAccessible(isAccessible);

                try {
                    final Method setTextMethod = clazz.getDeclaredMethod("setText", String.class);
                    isAccessible = setTextMethod.isAccessible();
                    final String text;
                    text = class_root_node.getProperty(GraphRelationRegistry.getRelationAttributeProjectToken()).toString();
                    final Integer textId;
                    textId= (Integer) class_root_node.getProperty(GraphRelationRegistry.getRelationAttributeTokenId());
                    setTextMethod.setAccessible(true);
                    setTextMethod.invoke(entity, text + "-" + textId);
                    setTextMethod.setAccessible(isAccessible);
                    class_root_node.setProperty(GraphRelationRegistry.getRelationAttributeTokenId(), (textId + 1));
                } catch (NoSuchMethodException e) {
                    if (logger.isDebugEnabled())
                        logger.debug("no instance of issuebase. Continue");
                }

            } else {
                node = graphDb.getNodeById(id);
            }
            setProperties(node, entity);
            index_name.remove(node, method.getName());
            index_name.add(node, method.getName(), nameAtt);

            tx.success();
        } catch (NoSuchMethodException e) {
            logger.fatal("NoSuchMethodException: " + e.getMessage());

        } catch (IllegalAccessException e) {
            logger.fatal("IllegalAccessException: " + e.getMessage());

        } catch (InvocationTargetException e) {
            logger.fatal("InvocationTargetException: " + e.getMessage());

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

        if (logger.isDebugEnabled())
            logger.debug("setProperties: " + entity);

        final Field[] fieldNames = entity.getClass().getDeclaredFields();
        try {
            for (final Field field : fieldNames) {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if (field.isAnnotationPresent(Simple.class)) {
                    if (field.getType().equals(Date.class)) {
                        node.setProperty(field.getName(), field.get(entity) == null ? 0L : ((Date)field.get(entity))
                                .getTime());
                    } else {
                        if (field.get(entity) != null)
                            node.setProperty(field.getName(), field.get(entity));
                    }
                }
                else if (field.isAnnotationPresent(Relational.class)) {
                    final Class aClass = field.getAnnotation(Relational.class).clazz();

                    if (field.getType().equals(List.class)) {
                        //get List from Entity
                        final DAO relDao = getDaoInstance(aClass);
                        final List entityList = (List)field.get(entity);
                        final Iterator it = entityList.iterator();
                        Long[] ids = new Long[entityList.size()];
                        int i = 0;

                        //persist comments
                        while (it.hasNext()) {
                            Object single = it.next();
                            if (relDao != null)
                                daoFactory.saveOrUpdateTX(single);
                            ids[i++] = getIdFromEntity(single, aClass);
                        }

                        //remove deleted Objects from relDB
                        final long[] nodeIds = (long[])node.getProperty(field.getName(), new long[]{});
                        boolean deleteInRel;
                        for (long singleNodeId : nodeIds) {
                            deleteInRel = true;
                            for (Object singleEntity : entityList) {
                                if (getIdFromEntity(singleEntity, aClass) == (singleNodeId)) {
                                    deleteInRel = false;
                                    break;
                                } else {
                                    deleteInRel = true;
                                }
                            }
                            if (deleteInRel) {
                                daoFactory.removeTX(relDao.findByID(singleNodeId));
                            }
                        }

                        node.setProperty(field.getName(), ids);
                    } else {
                        if (field.get(entity) != null)
                                node.setProperty(field.getName(), getIdFromEntity(field.get(entity), aClass));
                    }
                }
                else if (field.isAnnotationPresent(Graph.class)) {
                    if (field.get(entity) != null) {
                        final Class aClass = field.getAnnotation(Graph.class).clazz();
                        Long id = getIdFromEntity(field.get(entity), aClass);
                        if (id != null && id != 0) {
                            connectSingleAttribute(node, graphDb.getNodeById(id), aClass);
                        }
                    }
                }
                else if (field.isAnnotationPresent(LazyGraphPersist.class)) {
                    final Object fieldValue = field.get(entity);
                    if (fieldValue != null) {
                        final Map valueMap = Map.class.cast(fieldValue);

                        for (Iterator it = valueMap.keySet().iterator(); it.hasNext();) {
                            final Method method = Method.class.cast(it.next());
                            final List<Object[]> argsList = List.class.cast(valueMap.get(method));
                            for (Object[] args : argsList) {
                                method.invoke(this, args);
                            }
                        }
                    }
                    field.set(entity, null);
                }
                field.setAccessible(isAccessible);
            }
        } catch (IllegalAccessException e) {
            logger.fatal("IllegalAccessException: " + e.getMessage());
            //e.printStackTrace();
        } catch (InvocationTargetException e) {

        }
    }

    private void connectSingleAttribute(Node startNode, Node endNode, Class aClass) {
        if (logger.isDebugEnabled())
            logger.debug("connectSingleAttribute");

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
        if (logger.isDebugEnabled())
            logger.debug("connectAttribute");

        if (startNode == null)
            throw new NullPointerException("Startnode is null.");
        if (endNode == null)
            throw new NullPointerException("Endnode is null.");
        if (aClass == null)
            throw new NullPointerException("Class is null.");

        startNode.createRelationshipTo(endNode, GraphRelationRegistry.getRelationshipTypeForClass(aClass));
    }


    public List<T> loadAllEntities() {
        if (logger.isDebugEnabled())
            logger.debug("loadAllEntities");

        final TraversalDescription td = Traversal.description()
                .depthFirst()
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
        if (logger.isDebugEnabled())
            logger.debug("loadTopLevelEntities");

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

    public T findByID(final Long id) {
        if (logger.isDebugEnabled())
            logger.debug("findByID");

        if (id == null)
            throw new NullPointerException("Id object is null.");

        return getObjectFromNode(graphDb.getNodeById(id));
    }

    public boolean existInDatabase(final Long id) {
        if (logger.isDebugEnabled())
            logger.debug("existInDatabase");

        if (id == null)
            throw new NullPointerException("Id object is null.");

        try {
            graphDb.getNodeById(id);
        } catch (NotFoundException e){
            logger.warn("Issue has been deleted");
            return false;
        }
        return true;
    }

    protected T getObjectFromNode(final Node node) {
        if (logger.isDebugEnabled())
            logger.debug("getObjectFromNode: " + clazz.getSimpleName());

        return this.<T>getObjectFromNode(node , clazz);
    }

    protected <E> E getObjectFromNode(final Node node, final Class clazz) {
        if (logger.isDebugEnabled())
            logger.debug("getObjectFromNode: " + clazz.getSimpleName());

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
                    if (logger.isDebugEnabled())
                        logger.debug("Get constructor with parameter: " + clazz.getSimpleName());
                    entity = (E) clazz.getConstructor(Long.class).newInstance(-1L);
                } catch (NoSuchMethodException e1) {
                    logger.fatal("NoSuchMethodException" + e.getMessage());
                } catch (InvocationTargetException e1) {
                    logger.fatal("InvocationTargetException" + e.getMessage());
                }
            } catch (InvocationTargetException e) {
                logger.fatal("InvocationTargetException" + e.getMessage());

            }

            final Field[] fieldNames = entity.getClass().getDeclaredFields();
            for (final Field field : fieldNames) {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if (field.isAnnotationPresent(Identifier.class)) {
                    field.set(entity, field.getType().cast(node.getId()));
                }

                if (field.isAnnotationPresent(Simple.class)) {
                    if (field.getType().equals(Date.class)) {
                        field.set(entity, new Date(Long.class.cast(node.getProperty(field.getName()))));

                    } else
                        field.set(entity, field.getType().cast(node.getProperty(field.getName(), null)));
                }

                else if (field.isAnnotationPresent(Relational.class)) {
                    //Load via relational DAO's
                    final DAO relDao = getDaoInstance(field.getAnnotation(Relational.class).clazz());
                    if (relDao != null) {
                        if (field.getType().equals(List.class)) {
                            final long[] entityList = (long[])node.getProperty(field.getName(), new long[]{});
                            final List<Object> ids = new ArrayList<>();
                            Object obj = null;
                            for (Object single : entityList) {
                                obj = relDao.findByID((Long)single);
                                if (obj != null)
                                    ids.add(obj);
                                else logger.error("Requested object not in rel databse: " + field.getAnnotation
                                        (Relational.class).clazz().getSimpleName() + " " + (Long) single);
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

                    Relationship rel = node.getSingleRelationship(GraphRelationRegistry.getRelationshipTypeForClass(aClass),
                            Direction.OUTGOING);
                    if (rel != null) {
                        Node otherNode = rel.getOtherNode(node);
                        if (otherNode != null)
                            field.set(entity, getObjectFromNode(otherNode, aClass));
                    }
                }
                field.setAccessible(isAccessible);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e);
        }
        return entity;
    }

    protected Long getIdFromEntity(final Object entity) {
        return getIdFromEntity(entity, clazz);
    }


    protected Long getIdFromEntity(final Object entity, final Class aClass) {
        if (logger.isDebugEnabled())
            logger.debug("getIdFromEntity");

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
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e);
        }
        return id;
    }

    private DAO getDaoInstance(final Class aClass) {
        if (logger.isDebugEnabled())
            logger.debug("getDaoInstance");

        if (aClass == null)
            throw new NullPointerException("Class is null");

        DAO relDao = null;
        if (daoFactory != null) {
            final Method method;
            try {
                method = daoFactory.getClass().getDeclaredMethod("get" + aClass.getSimpleName() + "DAO");
                relDao = (DAO)method.invoke(daoFactory);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                logger.error(e);
            }
        }
        return relDao;
    }


    protected boolean deleteSimpleAttribute(final T entity) {
        if (entity == null)
            throw new NullPointerException("Object to delete can't be null.");

        final Long entityId = getIdFromEntity(entity);
        if (entityId == null)
            throw new IllegalArgumentException("Entity Id can't be null. Persist first.");

        if (logger.isDebugEnabled())
            logger.debug("delete: " + entity);

        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try{
            final Node node = graphDb.getNodeById(entityId);
            for (Relationship rel : node.getRelationships()) {
                rel.delete();
            }
            node.delete();
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }

    protected boolean deleteAttribute(final T entity, final T assignTo) {
        if (entity == null)
            throw new NullPointerException("Object to delete can't be null.");

        final Long entityId = getIdFromEntity(entity);
        if (entityId == null)
            throw new IllegalArgumentException("Entity Id can't be null. Persist first.");

        if (assignTo == null)
            throw new NullPointerException("Object deleted objects get assigned to can't be null.");

        final Long assignToId = getIdFromEntity(assignTo);
        if (assignToId == null)
            throw new IllegalArgumentException("Entity Id to assign to can't be null. Persist first.");

        if (logger.isDebugEnabled())
            logger.debug("delete: " + entity);


        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try{
            final Node entityNode = graphDb.getNodeById(entityId);
            final Node assignToNode = graphDb.getNodeById(assignToId);
            //rearrange Issues
            final RelationshipType relType = GraphRelationRegistry.getRelationshipTypeForClass(clazz);
            for (Relationship rel : entityNode.getRelationships()) {
                if (rel.isType(relType) && rel.getEndNode().equals(entityNode)) {
                       rel.getStartNode().createRelationshipTo(assignToNode, relType);
                }
                rel.delete();
            }
            entityNode.delete();

            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }


    protected boolean deleteIssue(final T entity) {
        if (entity == null)
            throw new NullPointerException("Object to delete can't be null.");

        final Long id = getIdFromEntity(entity);
        if (id == null)
            throw new IllegalArgumentException("Issue Id cant be null. Persist first.");

        if (logger.isDebugEnabled())
            logger.debug("delete: " + entity);

        boolean success = false;

        final Field[] fieldNames = entity.getClass().getDeclaredFields();
        for (final Field field : fieldNames) {
            if (field.isAnnotationPresent(Relational.class)) {
                if (field.getType().equals(List.class) && field.getAnnotation(Relational.class).onDeleteCascade()) {
                    boolean isAccessible = field.isAccessible();
                    field.setAccessible(true);
                    try {
                        field.set(entity, new ArrayList<>());
                    } catch (IllegalAccessException e) {

                    }
                    field.setAccessible(isAccessible);
                }

            }
        }
        this.persist(entity);

        final Transaction tx = graphDb.beginTx();
        try{
            final Node node = graphDb.getNodeById(id);
            //rearrange Subissues
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
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }


    protected boolean deleteRelation(final T entity) {
        if (entity == null)
            throw new NullPointerException("Object to delete can't be null.");

        final Long id = getIdFromEntity(entity);
        if (id == null)
            throw new IllegalArgumentException("Relation Id cant be null. Persist first.");

        if (logger.isDebugEnabled())
            logger.debug("delete: " + entity);

        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try{
            final Node relNode = graphDb.getNodeById(id);
            final RelationshipType relType = GraphRelationRegistry.getRelationshipTypeForClass(IssueRelation.class);
            for (Relationship rel : relNode.getRelationships(relType, Direction.INCOMING)) {
                final Node issueNode = rel.getOtherNode(relNode);
                for (Relationship relIssue : issueNode.getRelationships((RelationshipType)entity, Direction.OUTGOING)) {
                    relIssue.delete();
                }
                rel.delete();
            }

            relNode.getSingleRelationship(GraphRelationRegistry.getClassRootToChildRelType(),
                    Direction.INCOMING).delete();

            relNode.delete();
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }


    protected List<IssueBase> getConnectedIssuesFromProject(final T entity, final Long projectId) {
        if (entity == null)
            throw new NullPointerException("Entity is null");

        final Long id = getIdFromEntity(entity);
        if (id == null)
            throw new IllegalArgumentException("Entity Id cant be null. Persist first.");

        if (projectId < 0)
            throw new IllegalArgumentException("ProjectId must be positiv");

        if (logger.isDebugEnabled())
            logger.debug(this.getClass().getSimpleName() + ".getConnectedIssuesFromProject: " + projectId);

        final List<IssueBase> issueList = new ArrayList<>();
        final Node statusNode = graphDb.getNodeById(id);
        if (statusNode == null)
            throw new NullPointerException("Could not find item in Database.");
        IssueBase issue = null;
        final RelationshipType relType = GraphRelationRegistry.getRelationshipTypeForClass(clazz);
        for (Relationship rel : statusNode.getRelationships(relType, Direction.INCOMING)) {
            issue = getObjectFromNode(rel.getOtherNode(statusNode), IssueBase.class);
            if (issue != null && (projectId == 0 || issue.getProjectid().equals(projectId))) {
                issueList.add(issue);
                if (logger.isDebugEnabled())
                    logger.debug("Is connected Issues: " + issue);
            } else
            if (logger.isDebugEnabled())
                logger.debug("Is not connected: " + issue);
        }
        return issueList;
    }
}


