package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;

import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 15:45
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class AddProjectWindow extends Window{

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
    private AddProjectWindowBean bean;

    public AddProjectWindow(final MainUI ui, ResourceBundle messages) {
        this.ui = ui;
        this.messages = messages;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setPositionX(POSITION_X);
        setPositionY(POSITION_Y);

        bean = EJBFactory.getEjbInstance(AddProjectWindowBean.class);
        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        refreshEntities(baseDaoFactoryBean);

        final PlannedProject projekt = new PlannedProject();
        fieldGroup = new ProjektFieldGroup(projekt);

        fillFormLayout();
        addComponent(formLayout);

        horizontalButtonLayout.addComponent(saveButton);
        horizontalButtonLayout.addComponent(cancelButton);

        addComponent(horizontalButtonLayout);

        addListeners(baseDaoFactoryBean, projekt, ui);
        doInternationalization();

    }

    private void fillFormLayout() {
        final TextField idField = new TextField(PlannedProject.ID);
        idField.setValue("autom.");
        idField.setRequired(true);
        idField.setEnabled(false);
        formLayout.addComponent(idField);
        for (final Object propertyId : fieldGroup.getBoundPropertyIds()) {
            final TextField field = (TextField)fieldGroup.getField(propertyId);
            field.setReadOnly(false);
            formLayout.addComponent(field);
        }
    }

    private void doInternationalization() {
        this.setCaption(messages.getString("pm_addproject"));
        saveButton.setCaption(messages.getString("save"));
        cancelButton.setCaption(messages.getString("cancel"));
    }

    private void addListeners(final DaoFactoryBean baseDaoFactoryBean, PlannedProject proj, final MainUI ui) {
        final PlannedProject projekt = proj;
        saveButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean allFilled = true;
                Iterator<Component> it = AddProjectWindow.this.formLayout
                        .getComponentIterator();
                while (it.hasNext()) {
                    final Component component = it.next();
                    if (component instanceof AbstractField) {
                        if (((TextField) component).getValue() == null
                                || ((TextField) component).getValue().equals(""))
                            allFilled = false;
                    }
                }
                if (allFilled) {
                    try {
                        fieldGroup.commit();
                        final BeanItem<PlannedProject> beanItem = (BeanItem<PlannedProject>) fieldGroup
                                .getItemDataSource();
                        baseDaoFactoryBean.saveOrUpdate(beanItem.getBean());
                        AddProjectWindow.this.close();
                        ui.setWorkingArea(new ProjectAdministrationScreen(ui));
                    } catch (FieldGroup.CommitException e) {
                        logger.warn(e);
                    }

                } else {
                    final Label lbl = new Label();
                    lbl.setValue(messages.getString("stdsatz_fillInAllFields"));
                    AddProjectWindow.this.addComponent(lbl);
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

    private void refreshEntities(DaoFactoryBean baseDaoFactoryBean) {
        final EntityManager entityManager = baseDaoFactoryBean.getEntityManager();
        for(final PlannedProject plannedProject : baseDaoFactoryBean.getPlannedProjectDAO().loadAllEntities()){
            entityManager.refresh(plannedProject);
        }
        for(final PlanningUnit planningUnit : baseDaoFactoryBean.getPlanningUnitDAO().loadAllEntities()){
            entityManager.refresh(planningUnit);
        }
        for(final PlanningUnitElement planningUnitElement : baseDaoFactoryBean.getPlanningUnitElementDAO().loadAllEntities()){
            entityManager.refresh(planningUnitElement);
        }
        for(final RessourceGroup ressourceGroup : baseDaoFactoryBean.getRessourceGroupDAO().loadAllEntities()){
            entityManager.refresh(ressourceGroup);
        }
    }
}
