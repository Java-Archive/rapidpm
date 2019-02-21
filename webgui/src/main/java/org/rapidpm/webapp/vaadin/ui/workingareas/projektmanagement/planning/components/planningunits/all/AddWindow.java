package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import org.apache.log4j.Logger;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.exceptions.SameNameException;

import javax.naming.InvalidNameException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 21.10.12
 * Time: 10:37
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class AddWindow extends RapidWindow {

    public static final String HEIGHT = "600px";
    public static final String WIDTH = "750px";
    public static final int POSITION_X = 80;
    public static final int POSITION_Y = 20;

    private static final Logger logger = Logger.getLogger(AddWindow.class);

    private VerticalLayout singleLayout = new VerticalLayout();
    private FormLayout formLayout = new FormLayout();
    private HorizontalLayout horizontalButtonLayout = new HorizontalLayout();
    private Button saveButton = new Button();
    private Button cancelButton = new Button();
    private PlanningUnitFieldGroup fieldGroup;
    private ResourceBundle messages;

    public AddWindow(final ProjektplanungScreen screen) {
        messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
//        setHeight(HEIGHT);
//        setWidth(WIDTH);
//        setPositionX(POSITION_X);
//        setPositionY(POSITION_Y);

        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();

        fieldGroup = new PlanningUnitFieldGroup(screen);

        fillFormLayout();
        singleLayout.add(formLayout);
        horizontalButtonLayout.add(saveButton);
        horizontalButtonLayout.add(cancelButton);

        singleLayout.add(horizontalButtonLayout);
        singleLayout.setSpacing(true);
        add(singleLayout);
        addListeners(daoFactory, screen);
        doInternationalization();

    }

    private void fillFormLayout() {
        fieldGroup.getFieldForProperty(PlanningUnit.NAME).ifPresent(abstractField -> formLayout.add(abstractField));
        fieldGroup.getFieldForProperty(PlanningUnit.PARENT).ifPresent(abstractField -> formLayout.add(abstractField));
        fieldGroup.getFieldForProperty(PlanningUnit.RESPONSIBLE).ifPresent(abstractField -> formLayout.add(abstractField));
        fieldGroup.getFieldForProperty(PlanningUnit.STORYPTS).ifPresent(abstractField -> formLayout.add(abstractField));
        fieldGroup.getFieldForProperty(PlanningUnit.COMPLEXITY).ifPresent(abstractField -> formLayout.add(abstractField));
        fieldGroup.getFieldForProperty(PlanningUnit.ORDERNUMBER).ifPresent(abstractField -> formLayout.add(abstractField));
    }

    private void doInternationalization() {
//        this.setText(messages.getString("planning_addPlanningUnit"));
        saveButton.setText(messages.getString("save"));
        cancelButton.setText(messages.getString("cancel"));
    }

    private void addListeners(final DaoFactory daoFactory,
                              final ProjektplanungScreen screen) {
        saveButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            try {
                final PlannedProject projekt = daoFactory.getPlannedProjectDAO().findByID(VaadinSession.getCurrent().getAttribute(PlannedProject.class).getId());
                //PlanningUnitBeanItem mit der neuen (transienten) PlanningUnit
                final PlanningUnit newPlanningUnit = new PlanningUnit();
                fieldGroup.writeBean(newPlanningUnit);
                final String newPlanningUnitName = newPlanningUnit.getPlanningUnitName();
                if(newPlanningUnitName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN))
                    throw new InvalidNameException();
                if(newPlanningUnit.getParent() != null ){
                    final String parentsPlanningUnitName = newPlanningUnit.getParent().getPlanningUnitName();
                    if(newPlanningUnitName.equals(parentsPlanningUnitName)){
                        throw new SameNameException();
                    }
                    if(newPlanningUnit.getParent().getId() == ProjektplanungScreen.PLATZHALTER_ID){
                        newPlanningUnit.setParent(null);
                    }
                }
                newPlanningUnit.setPlanningUnitElementList(null);
                daoFactory.saveOrUpdateTX(newPlanningUnit);
                newPlanningUnit.setKindPlanningUnits(new HashSet<>());
                if(newPlanningUnit.getParent() != null ){
                    final PlanningUnit parentPlanningUnit = daoFactory.getPlanningUnitDAO().findByID
                            (newPlanningUnit.getParent().getId());
                    parentPlanningUnit.getKindPlanningUnits().add(newPlanningUnit);
                    daoFactory.saveOrUpdateTX(parentPlanningUnit);
                }

                final List<RessourceGroup> ressourceGroups = daoFactory.getRessourceGroupDAO()
                        .loadAllEntities();
                if(newPlanningUnit.getParent() != null){
                    final Set<PlanningUnit> geschwisterPlanningUnits = newPlanningUnit.getParent().getKindPlanningUnits();
                    if(geschwisterPlanningUnits == null || geschwisterPlanningUnits.size() <= 1){
                        newPlanningUnit.setPlanningUnitElementList(new ArrayList<>());
                        for(final PlanningUnitElement planningUnitElementFromParent : newPlanningUnit.getParent()
                                .getPlanningUnitElementList()){
                            final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                            planningUnitElement.setRessourceGroup(planningUnitElementFromParent.getRessourceGroup());
                            planningUnitElement.setPlannedMinutes(planningUnitElementFromParent.getPlannedMinutes());
                            daoFactory.saveOrUpdateTX(planningUnitElement);
                            newPlanningUnit.getPlanningUnitElementList().add(planningUnitElement);
                        }
                    } else {
                        createNewPlanningUnitElements(newPlanningUnit, ressourceGroups, daoFactory);
                    }
                } else {
                    createNewPlanningUnitElements(newPlanningUnit, ressourceGroups, daoFactory);
                }
                daoFactory.saveOrUpdateTX(newPlanningUnit);
                if(newPlanningUnit.getParent() == null ){
                    projekt.getPlanningUnits().add(newPlanningUnit);
                }
                daoFactory.saveOrUpdateTX(projekt);
                daoFactory.getEntityManager().refresh(projekt);
                AddWindow.this.close();
            } catch (final InvalidNameException e) {
                Notification.show(messages.getString("planning_invalidname"));
            } catch (final SameNameException e) {
                Notification.show(messages.getString("planning_samename"));
            } catch (final Exception e) {
                logger.warn(e);
                Notification.show(messages.getString("incompletedata"));
            }
        });

        cancelButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> AddWindow.this.close());

    }

    //Erzeugt neue 00:00:00-PlanningUnitElements für die neue PlanningUnit und weist diese der PlanningUnit zu.
    private void createNewPlanningUnitElements(final PlanningUnit planningUnit,
                                               final List<RessourceGroup> ressourceGroups,
                                               final DaoFactory daoFactory) {
        planningUnit.setPlanningUnitElementList(new ArrayList<>());
        for(final RessourceGroup ressourceGroup : ressourceGroups){
            final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
            planningUnitElement.setPlannedMinutes(0);
            planningUnitElement.setRessourceGroup(ressourceGroup);
            planningUnit.getPlanningUnitElementList().add(planningUnitElement);
            daoFactory.saveOrUpdateTX(planningUnitElement);
        }
    }

    public void show() {
//        ui.addWindow(this);
    }
}
