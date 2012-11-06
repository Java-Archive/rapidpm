package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.modell;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 05.11.12
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class SettingsDataContainer<T> extends IndexedContainer {
    private static Logger logger = Logger.getLogger(SettingsDataContainer.class);

    private final GraphBaseDAO<T> dao;
    private final Class clazz;
    private List<Object> visibleColumns;

    public SettingsDataContainer(Class aClass) {
        super();
        clazz = aClass;
        dao = getGraphDAOInstance();
        visibleColumns = new ArrayList<>();


        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Simple.class)) {
                this.addContainerProperty(field.getName(), field.getType(), null);
                visibleColumns.add(field.getName());
            }
        }
        fillTableWithDaoEntities();
    }

    public List<Object> getVisibleColumns() {
        return visibleColumns;
    }

    public void fillTableWithDaoEntities() {
        if (logger.isDebugEnabled())
            logger.debug("Fill table with DAO entities");
        this.removeAllItems();
        for (T entity : dao.loadAllEntities()) {
            addEntityToTable(entity);
        }
    }

    public void addEntityToTable(final T entity) {
        final Field[] fieldnames = clazz.getDeclaredFields();

        if (logger.isDebugEnabled())
            logger.debug("entity: " + entity);

        Item itemId = this.addItem(entity);
        for (Field field : fieldnames) {
            if (field.isAnnotationPresent(Simple.class)){
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                try {
                    Object val = field.get(entity);
                    itemId.getItemProperty(field.getName()).setValue(field.getType().cast(val));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                field.setAccessible(isAccessible);
            }
        }
    }

    private GraphBaseDAO<T> getGraphDAOInstance() {
        GraphBaseDAO<T> dao = null;
        final Method method;
        try {
            method = GraphDaoFactory.class.getDeclaredMethod("get" + clazz.getSimpleName() + "DAO");
            dao = (GraphBaseDAO<T>)method.invoke(GraphDaoFactory.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return dao;
    }

    public Object fillObjectFromItem(Object entity) {
        final Field[] fieldnames = clazz.getDeclaredFields();
        List<Object> itemProps = new ArrayList<>();
        Object prop;
        logger.info("PropIds: " + this.getContainerPropertyIds());
        for (Object propId : this.getContainerPropertyIds())
            itemProps.add(this.getContainerProperty(entity, propId).getValue());

        int i= 0;
        if (logger.isDebugEnabled())
            logger.debug("fillObjectFromItem: " + entity);

        for (Field field : fieldnames) {
            if (field.isAnnotationPresent(Simple.class)){
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                try {
                    if (i < itemProps.size()) {
                        if (logger.isDebugEnabled())
                            logger.debug("Property Value: " + itemProps.get(i) + " in " + field.getName());
                        prop = itemProps.get(i);

                        if (!field.getName().contains("FileName")) {
                            if (prop == null || prop.equals("null") || prop.equals("")) {
                                if (logger.isDebugEnabled())
                                    logger.debug("null value found: " + field.getName());
                                return null;
                            }
                        } else
                            if (prop == "") prop = null;

                        field.set(entity, field.getType().cast(prop));


                    }
                    i++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                field.setAccessible(isAccessible);
            }
        }
        return entity;
    }

    public Object persistItem(Object entity) {
        entity = dao.persist((T)entity);
        fillTableWithDaoEntities();
        return entity;
    }

    public boolean removeConnectedItem(Object entity, Object assignTo){
        boolean success = false;
        logger.info("delete item: " + entity + " and assign to" + assignTo);

        if (dao.deleteAttribute((T) entity,(T) assignTo))
            if (this.removeItem(entity))
                success = true;
        fillTableWithDaoEntities();
        return success;
    }

    public boolean removeSimpleItem(Object entity) {
        boolean success = false;
        logger.info("delete item: " + entity);

        if (dao.deleteSimpleAttribute((T) entity))
            if (this.removeItem(entity))
                success = true;
        fillTableWithDaoEntities();
        return success;
    }

    public boolean cancelAddingEditing(Object entity) {
        boolean success = true;
        logger.info("cancelAddingEditing: entity " + entity);
        Object prop = this.getContainerProperty(entity, this.getContainerPropertyIds().iterator().next()).getValue();
        if (prop == null || prop.equals("") || prop.equals("null"))
            success = this.removeItem(entity);
        return success;
    }
}
