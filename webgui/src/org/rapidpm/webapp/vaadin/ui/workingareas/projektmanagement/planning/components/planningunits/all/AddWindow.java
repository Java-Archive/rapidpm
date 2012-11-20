package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.DefaultValues;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 21.10.12
 * Time: 10:37
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class AddWindow extends Window {

    public static final String HEIGHT = "600px";
    public static final String WIDTH = "750px";
    public static final int POSITION_X = 80;
    public static final int POSITION_Y = 20;

    private static final Logger logger = Logger.getLogger(AddWindow.class);

    private MainUI ui;

    private VerticalLayout singleLayout = new VerticalLayout();
    private FormLayout formLayout = new FormLayout();
    private HorizontalLayout horizontalButtonLayout = new HorizontalLayout();
    private Button saveButton = new Button();
    private Button cancelButton = new Button();
    private PlanningUnitFieldGroup fieldGroup;
    private ResourceBundle messages;

    public AddWindow(final MainUI ui, final ProjektplanungScreen screen) {
        this.ui = ui;
        messages = screen.getMessagesBundle();
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setPositionX(POSITION_X);
        setPositionY(POSITION_Y);

        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();

        fieldGroup = new PlanningUnitFieldGroup(screen);

        fillFormLayout();
        singleLayout.addComponent(formLayout);
        horizontalButtonLayout.addComponent(saveButton);
        horizontalButtonLayout.addComponent(cancelButton);

        singleLayout.addComponent(horizontalButtonLayout);
        singleLayout.setSpacing(true);
        setContent(singleLayout);
        addListeners(daoFactory, ui, screen);
        doInternationalization();

    }

    private void fillFormLayout() {
        formLayout.addComponent(fieldGroup.getNameField());
        formLayout.addComponent(fieldGroup.getDescriptionArea());
        formLayout.addComponent(fieldGroup.getParentBox());
        formLayout.addComponent(fieldGroup.getResponsibleBox());
        formLayout.addComponent(fieldGroup.getStoryPointsField());
        formLayout.addComponent(fieldGroup.getComplexityField());
        formLayout.addComponent(fieldGroup.getOrderNumberField());
    }

    private TextField buildFieldWithValue(final AbstractTextField abstractTextField) {
        final TextField field = (TextField) abstractTextField;
        final String typeNameOfFieldProperty = field.getPropertyDataSource().getType().getSimpleName();
        field.setValue(DefaultValues.valueOf(typeNameOfFieldProperty).getDefaultValue());
        return field;
    }

    private void doInternationalization() {
        this.setCaption(messages.getString("stdsatz_addRessource"));
        saveButton.setCaption(messages.getString("save"));
        cancelButton.setCaption(messages.getString("cancel"));
    }

    private void addListeners(final DaoFactory daoFactory, final MainUI ui,
                              final ProjektplanungScreen screen) {
        saveButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                    try {
                        final PlannedProject projekt = daoFactory.getPlannedProjectDAO().findByID(ui
                                .getCurrentProject().getId());
                        fieldGroup.commit();
                        //PlanningUnitBeanItem mit der neuen (transienten) PlanningUnit
                        final BeanItem<PlanningUnit> beanItem = (BeanItem)fieldGroup.getItemDataSource();
                        //Bean aus dem BeanItem
                        final PlanningUnit planningUnit = beanItem.getBean();
                        if(planningUnit.getParent() == null ){
                            planningUnit.setKindPlanningUnits(new HashSet<PlanningUnit>());


                        } else {
                            final PlanningUnit parentPlanningUnit = daoFactory.getPlanningUnitDAO().findByID
                                    (planningUnit.getParent().getId());
                            parentPlanningUnit.getKindPlanningUnits().add(planningUnit);
                            daoFactory.saveOrUpdate(parentPlanningUnit);
                        }
                        final List<RessourceGroup> ressourceGroups = daoFactory.getRessourceGroupDAO()
                                .loadAllEntities();
                        for(final RessourceGroup ressourceGroup : ressourceGroups){
                            final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                            daoFactory.getEntityManager().persist(planningUnitElement);
                            planningUnitElement.setPlannedDays(0);
                            planningUnitElement.setPlannedHours(0);
                            planningUnitElement.setPlannedMinutes(0);
                            planningUnitElement.setRessourceGroup(ressourceGroup);
                            daoFactory.saveOrUpdateTX(planningUnitElement);
                            planningUnit.getPlanningUnitElementList().add(planningUnitElement);
                        }
                        daoFactory.saveOrUpdateTX(planningUnit);
                        projekt.getPlanningUnits().add(planningUnit);
                        daoFactory.saveOrUpdateTX(projekt);
                        daoFactory.getEntityManager().refresh(projekt);
                        AddWindow.this.close();
                        final MainUI ui = screen.getUi();
                        ui.setWorkingArea(new ProjektplanungScreen(ui));
                    } catch (FieldGroup.CommitException e) {
                        logger.warn(e);
                        Notification.show(messages.getString("incompletedata"));
                    }
            }
        });

        cancelButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                AddWindow.this.close();
            }
        });

    }

    public void show() {
        ui.addWindow(this);
    }
}
