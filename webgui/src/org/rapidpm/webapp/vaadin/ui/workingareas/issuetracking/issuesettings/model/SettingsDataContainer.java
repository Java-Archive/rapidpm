package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.model;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.UI;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.NonVisible;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.webapp.vaadin.BaseUI;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.11.12
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class SettingsDataContainer<T> extends IndexedContainer {
    private static Logger logger = Logger.getLogger(SettingsDataContainer.class);

    private final GraphBaseDAO<T> dao;
    private final Class clazz;
    private List<Object> visibleColumns;

    public SettingsDataContainer(final Class aClass) {
        if (aClass == null)
            throw new NullPointerException("Class must not be null");

        clazz = aClass;
        dao = getDAOInstance();
        visibleColumns = new ArrayList<>();


        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Simple.class) && !field.isAnnotationPresent(NonVisible.class)) {
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
        //TODO
        for (T entity : dao.loadAllEntities(((BaseUI)UI.getCurrent()).getCurrentProject().getId())) {
            addEntityToTable(entity);
        }
    }

    public void addEntityToTable(final T entity) {
        final Field[] fieldnames = clazz.getDeclaredFields();

        if (logger.isDebugEnabled())
            logger.debug("entity: " + entity);

        Item itemId = this.addItem(entity);
        for (Field field : fieldnames) {
            if (field.isAnnotationPresent(Simple.class) && !field.isAnnotationPresent(NonVisible.class)){
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                try {
                    Object val = field.get(entity);
                    itemId.getItemProperty(field.getName()).setValue(field.getType().cast(val));
                } catch (IllegalAccessException e) {

                }
                field.setAccessible(isAccessible);
            }
        }
    }

    private GraphBaseDAO<T> getDAOInstance() {
        GraphBaseDAO<T> dao = null;
        final Method method;
        try {
            method = DaoFactory.class.getDeclaredMethod("get" + clazz.getSimpleName() + "DAO");
            dao = (GraphBaseDAO<T>)method.invoke(DaoFactorySingelton.getInstance());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
         logger.error(e);
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
            if (field.isAnnotationPresent(Simple.class) && !field.isAnnotationPresent(NonVisible.class)){
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                try {
                    if (i < itemProps.size()) {
                        if (logger.isDebugEnabled())
                            logger.debug("Property Value: " + itemProps.get(i) + " in " + field.getName());
                        prop = itemProps.get(i);

                        if (!field.getName().contains("FileName")) {
                            if (prop == null || prop.equals("null") || prop.equals("")
                                    || prop.toString().trim().equals("")) {
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
                    logger.error(e);
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
        if (entity == null)
            throw new NullPointerException("Entity to remove is null!");
        if (assignTo == null)
            throw new NullPointerException("Entity to assign to is null!");
        if (entity.getClass() != clazz)
            throw new IllegalArgumentException("Entity class is not " + clazz.getSimpleName());
        if (assignTo.getClass() != clazz)
            throw new IllegalArgumentException("'assignTo' entity class is not " + clazz.getSimpleName());

        boolean success = false;
        logger.info("delete item: " + entity + " and assign to" + assignTo);

        if (daoDeleteMethod(new Object[]{entity, (T) assignTo}))
            if (this.removeItem(entity))
                success = true;
        fillTableWithDaoEntities();
        return success;
    }

    public boolean removeSimpleItem(Object entity) {
        if (entity == null)
            throw new NullPointerException("Entity to remove is null!");
        if (entity.getClass() != clazz)
            throw new IllegalArgumentException("Entity class is not " + clazz.getSimpleName());

        boolean success = false;
        logger.info("delete item: " + entity);

        //TODO auf konkrete DAO umbauen
        if (daoDeleteMethod(new Object[]{entity}))
            if (this.removeItem(entity))
                success = true;
        fillTableWithDaoEntities();
        return success;
    }

    private boolean daoDeleteMethod(Object[] args) {
        final Method method;
        boolean retVal = false;
        try {
            Class[] methodClassParams = new Class[args.length];
            int i = 0;
            for (Object obj : args) {
                methodClassParams[i++] = obj.getClass();
            }
            method = dao.getClass().getDeclaredMethod("delete", methodClassParams);
            retVal = (Boolean) method.invoke(dao, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e);
        }
        return retVal;
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
