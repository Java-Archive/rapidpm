package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.uicomponents;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.AbstractErrorMessage;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.IssueSettingsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.logic.SingleRowEditTableFieldFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 02.11.12
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
public class SettingLayout<T> extends VerticalLayout {
    private static Logger logger = Logger.getLogger(SettingLayout.class);

    private final Label headerLabel;
    private final Table contentTable;
    private final Label errorLabel;
    private final Class aClass;
    private final GraphBaseDAO<T> dao;

    private final Button addButton;
    private final Button editButton;
    private final Button deleteButton;
    private final Button saveButton;
    private final Button cancelButton;

    HorizontalLayout buttonHorLayout;
    VerticalLayout addButtonLayout;
    VerticalLayout saveButtonLayout;


    public SettingLayout(IssueSettingsScreen screen, String headerName, Class aClass) {
        this.aClass = aClass;

        dao = getGraphDAOInstance(aClass);

        buttonHorLayout = new HorizontalLayout();
        buttonHorLayout.setSizeFull();

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

        errorLabel = new Label();
        errorLabel.setWidth("100%");
        errorLabel.setContentMode(ContentMode.HTML);
        addComponent(errorLabel);

        contentTable = new Table();
        contentTable.setWidth("100%");
        contentTable.setImmediate(true);
        contentTable.setEditable(false);
        contentTable.setSelectable(true);
        contentTable.setPageLength(10);
        contentTable.addValueChangeListener(new ContentTableValueChangeListener());

        final Field[] fieldnames = this.aClass.getDeclaredFields();
        for (Field field : fieldnames) {
            if (field.isAnnotationPresent(Simple.class))
                contentTable.addContainerProperty(field.getName(), field.getType(), "");
        }

        fillTableWithDaoEntities();

        addButton = new Button(screen.getMessagesBundle().getString("add"));
        addButton.addClickListener(new AddEditButtonClickListener(true, aClass));
        addButton.setWidth("100%");
        addButton.setImmediate(true);
        addButton.setEnabled(true);

        editButton = new Button(screen.getMessagesBundle().getString("edit"));
        editButton.addClickListener(new AddEditButtonClickListener(false, aClass));
        editButton.setWidth("100%");
        editButton.setImmediate(true);
        editButton.setEnabled(false);

        deleteButton = new Button(screen.getMessagesBundle().getString("delete"));
        deleteButton.addClickListener(new DeleteButtonClickListener());
        deleteButton.setWidth("100%");
        deleteButton.setImmediate(true);
        deleteButton.setEnabled(false);

        addButtonLayout.addComponent(addButton);
        addButtonLayout.addComponent(editButton);
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


        buttonHorLayout.addComponent(contentTable);
        buttonHorLayout.addComponent(addButtonLayout);
        buttonHorLayout.setExpandRatio(contentTable, 1.0F);
        addComponent(buttonHorLayout);
    }

    private void addEntitiesToTable(final T entity) {
        final Field[] fieldnames = aClass.getDeclaredFields();

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

    private void fillTableWithDaoEntities() {
        if (logger.isDebugEnabled())
            logger.debug("Fill table with DAO entities");
        contentTable.removeAllItems();
        for (T entity : dao.loadAllEntities()) {
            addEntitiesToTable(entity);
        }
    }

    private Object fillObjectFromItem(Object entity, Item item) {
        final Field[] fieldnames = aClass.getDeclaredFields();
        List<Object> itemProps = new ArrayList<>();
        Object prop;
        for (Object itemId : item.getItemPropertyIds())
            itemProps.add(item.getItemProperty(itemId).getValue());

        int i= 0;
        if (logger.isDebugEnabled())
            logger.debug("fillObjectFromItem: " + entity + ", " + item);

        for (Field field : fieldnames) {
            if (field.isAnnotationPresent(Simple.class)){
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                try {
                    if (i < itemProps.size()) {
                        //if (logger.isDebugEnabled())
                            logger.info("Property Value: " + itemProps.get(i) + " in " + field.getName());
                        prop = itemProps.get(i);

                        if (!field.getName().contains("FileName"))
                            if (prop == null || prop.equals("null") || prop.equals("")) {
                                if (logger.isDebugEnabled())
                                    logger.debug("null value found: " + field.getName() + " in " + item);
                                return null;
                            }


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


    private class AddEditButtonClickListener implements Button.ClickListener {

        private final Class aClass;
        private final boolean isAddButton;

        public AddEditButtonClickListener(boolean isAddButton, Class aClass) {
            this.isAddButton = isAddButton;
            this.aClass = aClass;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            try {
                errorLabel.setValue("");

                final T entity;
                if (isAddButton) {
                    entity = (T) aClass.newInstance();
                    addEntitiesToTable(entity);
                    contentTable.select(entity);
                    if (logger.isDebugEnabled())
                        logger.debug("Add Entity: " + entity);
                } else {
                    entity = (T) contentTable.getValue();
                    if (logger.isDebugEnabled())
                        logger.debug("Edit Entity: " + entity);
                }

                contentTable.setSelectable(false);
                contentTable.setTableFieldFactory(new SingleRowEditTableFieldFactory(entity));
                contentTable.setEditable(true);
                buttonHorLayout.replaceComponent(addButtonLayout, saveButtonLayout);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    private class DeleteButtonClickListener implements Button.ClickListener {

        public DeleteButtonClickListener() {
        }
        @Override
        public void buttonClick(Button.ClickEvent event) {
            Object itemId = contentTable.getValue();
            logger.info("delete item: " + itemId);
            dao.delete((T) itemId);
            fillTableWithDaoEntities();
        }
    }


    private class SaveCancelButtonClickListener implements Button.ClickListener {
        private final boolean isSaveButton;

        public SaveCancelButtonClickListener(boolean isSaveButton) {
            this.isSaveButton = isSaveButton;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            Object entity = contentTable.getValue();
            final Item item = contentTable.getItem(entity);
            if (isSaveButton) {
               if (logger.isDebugEnabled()) {
                    logger.debug("SaveItem");
                    logger.debug("selected item: " + item);
                    logger.debug("selected entity: " + entity);
                }
                entity = fillObjectFromItem(entity, item);
                if (entity == null) {
                    if (logger.isInfoEnabled())
                        logger.info("No null values allowed in Item.");
                    errorLabel.setValue("<b><font color=\"red\">No null value allowed in item</font></b>");
                } else {
                    contentTable.setComponentError(null);
                    errorLabel.setValue("");
                    if (logger.isInfoEnabled())
                        logger.info("filled entity: " + entity);
                    try {
                        dao.persist((T)entity);
                    } catch (IllegalArgumentException e) {
                        errorLabel.setValue("<b><font color=\"red\">Name already in use</font></b>");
                    }
                }
                fillTableWithDaoEntities();
            } else {
                Object prop = item.getItemProperty(item.getItemPropertyIds().iterator().next()).getValue();
                //if (logger.isDebugEnabled()) {
                    logger.info("CancelEditing");
                    logger.info("selected item: " + item);
                    logger.info("prop: " + prop);
                //}
                if (prop == null || prop.equals("") || prop.equals("null")) {
                    contentTable.removeItem(entity);
                }
            }
            contentTable.setEditable(false);
            contentTable.setSelectable(true);
            buttonHorLayout.replaceComponent(saveButtonLayout, addButtonLayout);

        }
    }

    private class ContentTableValueChangeListener implements Property.ValueChangeListener{

        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            if (contentTable.getValue() != null) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        }
    }

}
