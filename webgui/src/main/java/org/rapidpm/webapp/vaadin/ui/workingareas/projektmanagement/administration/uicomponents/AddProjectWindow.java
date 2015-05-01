package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 15:45
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class AddProjectWindow extends RapidWindow{

    public static final String HEIGHT = "400px";
    public static final String WIDTH = "400px";
    public static final int POSITION_X = 50;
    public static final int POSITION_Y = 100;

    private static final Logger logger = Logger.getLogger(AddProjectWindow.class);

    private MainUI ui;

    private FormLayout formLayout = new FormLayout();
    private HorizontalLayout horizontalButtonLayout = new HorizontalLayout();
    private Button saveButton = new Button();
    private Button cancelButton = new Button();
    private ProjektFieldGroup fieldGroup;
    private ResourceBundle messages;
    private CheckBox makeCurrentProjectCheckBox;
//    private AddProjectWindowBean bean;

    public AddProjectWindow(final MainUI ui, final ResourceBundle messages) {
        this.ui = ui;
        this.messages = messages;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setPositionX(POSITION_X);
        setPositionY(POSITION_Y);

//        bean = EJBFactory.getEjbInstance(AddProjectWindowBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();

        final PlannedProject projekt = new PlannedProject();
        fieldGroup = new ProjektFieldGroup(projekt, messages);
        makeCurrentProjectCheckBox = new CheckBox(messages.getString("makeCurrentProject"));
        final List<PlannedProject> projectList = daoFactory.getPlannedProjectDAO().findAll();
        if(projectList == null || projectList.isEmpty()){
            makeCurrentProjectCheckBox.setValue(true);
            makeCurrentProjectCheckBox.setEnabled(false);
        }

        fillFormLayout();
        addComponent(formLayout);

        horizontalButtonLayout.addComponent(saveButton);
        horizontalButtonLayout.addComponent(cancelButton);

        addComponent(horizontalButtonLayout);
        addListeners(daoFactory, ui);
        doInternationalization();

    }

    private void fillFormLayout() {
        final TextField idField = new TextField(PlannedProject.ID);
        idField.setValue("autom.");
        idField.setRequired(true);
        idField.setEnabled(false);
        formLayout.addComponent(idField);
        for (final Object propertyId : fieldGroup.getBoundPropertyIds()) {
            final Field field = fieldGroup.getField(propertyId);
            field.setReadOnly(false);
            if(propertyId.toString().equals(PlannedProject.HOURSPERWORKINGDAY)){
                field.setValue(Constants.DEFAULT_HOURS_PER_WORKINGDAY);
                //als Standard 8 auswählen
                final Iterator iterator = ((ComboBox)field).getItemIds().iterator();
                for(int i=0; i<7; i++){
                   iterator.next();
                }
                ((ComboBox)field).setValue(iterator.next());
                //--
            }
            formLayout.addComponent(field);
        }
        formLayout.addComponent(makeCurrentProjectCheckBox);
    }

    private void doInternationalization() {
        this.setCaption(messages.getString("project_addproject"));
        saveButton.setCaption(messages.getString("save"));
        cancelButton.setCaption(messages.getString("cancel"));
    }

    private void addListeners(final DaoFactory baseDaoFactoryBean,final MainUI ui) {
        saveButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean allFilled = true;
                final Iterator<Component> it = AddProjectWindow.this.formLayout.getComponentIterator();
                while (it.hasNext()) {
                    final Component component = it.next();
                    if (component instanceof AbstractField) {
                        if (((AbstractField) component).getValue() == null
                                || ((AbstractField) component).getValue().equals(""))
                            allFilled = false;
                    }
                }
                if (allFilled) {
                    try {
                        fieldGroup.commit();
                        final BeanItem<PlannedProject> newProjectBeanItem = (BeanItem<PlannedProject>) fieldGroup
                                .getItemDataSource();
//                        baseDaoFactoryBean.saveOrUpdateTX(newProjectBeanItem.getBean());
                        if(makeCurrentProjectCheckBox.getValue() == true){
                            ui.getSession().setAttribute(PlannedProject.class, newProjectBeanItem.getBean());
                        }
                        AddProjectWindow.this.close();
                        ui.setWorkingArea(new ProjectAdministrationScreen(ui));
                    } catch (final FieldGroup.CommitException e) {
                        logger.warn(e);
                    }

                } else {
                    final Label lbl = new Label();
                    lbl.setValue(messages.getString("stdsatz_fillInAllFields"));
                    addComponent(lbl);
                }

            }

        });

        cancelButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                AddProjectWindow.this.close();
            }
        });

    }

    public void show() {
        ui.addWindow(this);
    }
}
