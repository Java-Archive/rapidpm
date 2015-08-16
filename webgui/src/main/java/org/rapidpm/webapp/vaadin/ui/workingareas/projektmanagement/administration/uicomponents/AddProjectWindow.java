package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.Constants;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 15:45
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class AddProjectWindow extends RapidWindow{

    public static final String HEIGHT = "500px";
    public static final String WIDTH = "500px";
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
                        final BeanItem<PlannedProject> newProjectBeanItem = (BeanItem<PlannedProject>) fieldGroup.getItemDataSource();
                        final PlannedProject persistedNewProject = baseDaoFactoryBean.getPlannedProjectDAO().createEntityFlat(newProjectBeanItem.getBean());
                        fillProjectWithPrototypePlanningUnit(persistedNewProject);
                        if(makeCurrentProjectCheckBox.getValue() == true){
                            ui.getSession().setAttribute(PlannedProject.class, persistedNewProject);
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

    private void fillProjectWithPrototypePlanningUnit(final PlannedProject persistedNewProject) {
        PlanningUnit firstPlanningUnit = new PlanningUnit();
        firstPlanningUnit.setOrderNumber(1);
        firstPlanningUnit.setPlanningUnitName("Prototyp");
        firstPlanningUnit.setKomplexitaet(1);
        firstPlanningUnit.setEstimatedStoryPoints(1);
        final List<RessourceGroup> ressourceGroups = DaoFactorySingleton.getInstance().getRessourceGroupDAO().findAll();
        final List<PlanningUnitElement> planningUnitElements = new ArrayList<>();
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
            planningUnitElement.setPlannedMinutes(0);
            planningUnitElement.setRessourceGroup(ressourceGroup);
                planningUnitElements.add(planningUnitElement);


        }
        firstPlanningUnit.setPlanningUnitElementList(planningUnitElements);
        try {
            firstPlanningUnit = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFull(firstPlanningUnit);
            DaoFactorySingleton.getInstance().getPlannedProjectDAO().addPlanningUnitToProject(firstPlanningUnit, persistedNewProject);
        } catch (NotYetImplementedException | InvalidKeyException | MissingNonOptionalPropertyException e) {
            e.printStackTrace();
        }
        if(persistedNewProject.getTopLevelPlanningUnits() == null){
            persistedNewProject.setPlanningUnits(new ArrayList<>());
        }
        persistedNewProject.getTopLevelPlanningUnits().add(firstPlanningUnit);
    }

    public void show() {
        ui.addWindow(this);
    }
}
