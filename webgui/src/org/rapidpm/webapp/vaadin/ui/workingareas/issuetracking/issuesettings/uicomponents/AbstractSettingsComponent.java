package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.uicomponents;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.IssueSettingsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.logic.SingleRowEditTableFieldFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 02.11.12
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
public class AbstractSettingsComponent<T> extends VerticalLayout {
    private static Logger logger = Logger.getLogger(AbstractSettingsComponent.class);

    private final IssueSettingsScreen screen;
    private final Label headerLabel;
//    private final String headerName;
    private final Table contentTable;
    private final Class aClass;
    private final GraphBaseDAO<T> dao;

    private final Button addButton;
    private final Button deleteButton;
    private final Button saveButton;
    private final Button cancelButton;

    VerticalLayout addButtonLayout;
    VerticalLayout saveButtonLayout;


    public AbstractSettingsComponent(IssueSettingsScreen screen, String headerName, Class aClass) {
        this.screen = screen;
//        this.headerName = headerName;
        this.aClass = aClass;

        dao = getGraphDAOInstance(aClass);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();

        addButtonLayout = new VerticalLayout();
        addButtonLayout.setSizeFull();
        addButtonLayout.setSpacing(true);
        addButtonLayout.setMargin(true);

        saveButtonLayout = new VerticalLayout();
        saveButtonLayout.setSizeFull();
        saveButtonLayout.setSpacing(true);
        saveButtonLayout.setMargin(true);


        headerLabel = new Label(headerName);
        headerLabel.setWidth("100%");
        addComponent(headerLabel);

        contentTable = new Table();
        contentTable.setWidth("100%");
        contentTable.setImmediate(true);
        contentTable.setEditable(false);
        contentTable.setSelectable(true);

        final Field[] fieldnames = this.aClass.getDeclaredFields();
        for (Field field : fieldnames) {
            if (field.isAnnotationPresent(Simple.class))
                contentTable.addContainerProperty(field.getName(), field.getType(), "");
        }

        fillTableWithEntitList(dao.loadAllEntities());

        addButton = new Button(screen.getMessagesBundle().getString("add"));
        addButton.addClickListener(new AddButtonClickListener(aClass));
        addButton.setWidth("100%");
        addButton.setImmediate(true);
        deleteButton = new Button(screen.getMessagesBundle().getString("delete"));
        deleteButton.addClickListener(new DeleteButtonClickListener(aClass));
        deleteButton.setWidth("100%");
        deleteButton.setImmediate(true);
        deleteButton.setEnabled(false);
        addButtonLayout.addComponent(addButton);
        addButtonLayout.addComponent(deleteButton);


        saveButton = new Button(screen.getMessagesBundle().getString("save"));
        saveButton.addClickListener(new SaveCancelButtonClickListener(true));
        saveButton.setWidth("100%");
        saveButton.setImmediate(true);
        cancelButton = new Button(screen.getMessagesBundle().getString("cancel"));
        cancelButton.addClickListener(new SaveCancelButtonClickListener(false));
        cancelButton.setWidth("100%");
        cancelButton.setImmediate(true);
        saveButtonLayout.addComponent(saveButton);
        saveButtonLayout.addComponent(cancelButton);

        horizontalLayout.addComponent(contentTable);
        horizontalLayout.addComponent(addButtonLayout);
        horizontalLayout.setExpandRatio(contentTable, 1.0F);
        addComponent(horizontalLayout);
    }

    private void fillTableWithEntitList(final List<T> entityList) {
        final Field[] fieldnames = aClass.getDeclaredFields();
        for (T entity : entityList) {
            if (logger.isDebugEnabled())
                logger.debug("entity: " + entity);
            List<Object> list = new ArrayList<>();
            for (Field field : fieldnames) {
                if (field.isAnnotationPresent(Simple.class)){
                    boolean isAccessible = field.isAccessible();
                    field.setAccessible(true);
                    try {
                        Object val = field.get(entity);
                        list.add(field.getType().cast(val));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    field.setAccessible(isAccessible);
                }
            }
            contentTable.addItem(list.toArray(), entity);
        }
    }

    private Object fillObjectFromItem(Object entity, Item item) {
        final Field[] fieldnames = aClass.getDeclaredFields();
        final Object[] itemIds = item.getItemPropertyIds().toArray();
        int i = 0;
        //if (logger.isDebugEnabled())
            logger.info("fillObjectFromItem: " + entity + ", " + item);

        for (Field field : fieldnames) {
            if (field.isAnnotationPresent(Simple.class)){
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                try {
                    logger.info("getVal");
                    Property p = item.getItemProperty(itemIds[i]);
                    Object prop = p.getValue();
                    logger.info("Property Value: " + prop + " in " + field.getName());
                    field.set(entity, field.getType().cast(prop));
                    logger.info("entity: " + entity);
                    i++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                field.setAccessible(isAccessible);
            }
        }
        return entity;
    }


    private GraphBaseDAO<T> getGraphDAOInstance(final Class aClass) {
        GraphBaseDAO<T> dao = null;
        final Method method;
        try {
            method = GraphDaoFactory.class.getDeclaredMethod("get" + aClass.getSimpleName() + "DAO");
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


    private class AddButtonClickListener implements Button.ClickListener {

        private final Class aClass;

        public AddButtonClickListener(Class aClass) {
            this.aClass = aClass;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            try {
                T entity = (T) aClass.newInstance();

                List<T> list = new ArrayList<>();
                list.add(entity);
                fillTableWithEntitList(list);
                if (logger.isDebugEnabled())
                    logger.debug("AddItem Entity: " + entity);

                contentTable.select(entity);
                contentTable.setSelectable(false);
                contentTable.setTableFieldFactory(new SingleRowEditTableFieldFactory(entity));
                contentTable.setEditable(true);
                replaceComponent(addButtonLayout, saveButtonLayout);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private class DeleteButtonClickListener implements Button.ClickListener {
        private final Class aClass;

        public DeleteButtonClickListener(Class aClass) {
            this.aClass = aClass;
        }
        @Override
        public void buttonClick(Button.ClickEvent event) {

        }
    }

    private class SaveCancelButtonClickListener implements Button.ClickListener {
        private final boolean saveButton;

        public SaveCancelButtonClickListener(boolean saveButton) {
            this.saveButton = saveButton;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            contentTable.setEditable(false);
            if (saveButton) {
                logger.info("SaveItem");
//                Object entity = contentTable.getValue();
//                final Item item = contentTable.getItem(entity);
//                logger.info("selected item: " + item);
//                logger.info("selected entity: " + entity);
//
//                final Field[] fieldnames = aClass.getDeclaredFields();
//                final Object[] itemIds = item.getItemPropertyIds().toArray();
//                int i = 0;
//                //if (logger.isDebugEnabled())
//                logger.info("fillObjectFromItem: " + entity + ", " + item);
//
//                for (Field field : fieldnames) {
//                    if (field.isAnnotationPresent(Simple.class)){
//                        boolean isAccessible = field.isAccessible();
//                        field.setAccessible(true);
//                        try {
//                            logger.info("getVal");
//                            Property p = item.getItemProperty(itemIds[i]);
//                            Object prop = p.getValue();
//                            logger.info("Property Value: " + prop + " in " + field.getName());
//                            field.set(entity, field.getType().cast(prop));
//                            logger.info("entity: " + entity);
//                            i++;
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                        }
//                        field.setAccessible(isAccessible);
//                    }
//                }
//                // return entity;
//
//
//                entity = fillObjectFromItem(entity, item);
//                logger.info("filled entity: " + entity);
                //dao.persist((T)contentTable.getValue());

            } else {
                logger.info("CancelEditing");
                contentTable.removeItem(contentTable.getValue());
            }
            contentTable.setSelectable(true);
            replaceComponent(saveButtonLayout, addButtonLayout);
        }
    }

}
