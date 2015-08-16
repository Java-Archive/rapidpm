package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

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
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.exceptions.SameNameException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.DefaultValues;

import javax.naming.InvalidNameException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();

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
        this.setCaption(messages.getString("planning_addPlanningUnit"));
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
                            .getCurrentProject().getId(), true);
                    fieldGroup.commit();
                    //PlanningUnitBeanItem mit der neuen (transienten) PlanningUnit
                    final BeanItem<PlanningUnit> beanItem = (BeanItem) fieldGroup.getItemDataSource();
                    //Bean aus dem BeanItem
                    final PlanningUnit newPlanningUnitTO = beanItem.getBean();
                    final String newPlanningUnitName = newPlanningUnitTO.getPlanningUnitName();
                    if (newPlanningUnitName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN))
                        throw new InvalidNameException();
                    if (newPlanningUnitTO.getParent() != null) {
                        final String parentsPlanningUnitName = newPlanningUnitTO.getParent().getPlanningUnitName();
                        if (newPlanningUnitName.equals(parentsPlanningUnitName)) {
                            throw new SameNameException();
                        }
                        if (newPlanningUnitTO.getParent().getId() == String.valueOf(ProjektplanungScreen.PLATZHALTER_ID)) {
                            newPlanningUnitTO.setParent(null);
                        }
                    }
                    final PlanningUnit persistedPlanningUnit = daoFactory.getPlanningUnitDAO().createEntityFlat(newPlanningUnitTO);
                    String responsiblePersonID = newPlanningUnitTO.getResponsiblePerson().getId();
                    if(responsiblePersonID != null && !responsiblePersonID.equals("")) {
                        final Benutzer responsiblePerson = daoFactory.getBenutzerDAO().findByID(responsiblePersonID, true);
                        daoFactory.getPlanningUnitDAO().addResponsiblePersonToPlanningUnit(responsiblePerson, persistedPlanningUnit);
                    }
                    persistedPlanningUnit.setPlanningUnitElementList(null);
                    persistedPlanningUnit.setKindPlanningUnits(new ArrayList<>());
                    if (newPlanningUnitTO.getParent() != null) {
                        final PlanningUnit parentPlanningUnit = daoFactory.getPlanningUnitDAO().findByID
                                (newPlanningUnitTO.getParent().getId(), true);
                        parentPlanningUnit.getKindPlanningUnits().add(newPlanningUnitTO);
                        daoFactory.getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(parentPlanningUnit, persistedPlanningUnit);
                    }

                    final List<RessourceGroup> ressourceGroups = daoFactory.getRessourceGroupDAO()
                            .findAll();
                    List<PlanningUnitElement> temporaryNewPUEsForPlanningUnit = new ArrayList<>();
                    final List<PlanningUnitElement> persistedNewPUEsForPlanningUnit = new ArrayList<>();
                    if (newPlanningUnitTO.getParent() != null) {
                        final List<PlanningUnit> geschwisterPlanningUnits = newPlanningUnitTO.getParent().getKindPlanningUnits();
                        if (geschwisterPlanningUnits == null || geschwisterPlanningUnits.size() <= 1) {
                            newPlanningUnitTO.setPlanningUnitElementList(new ArrayList<>());
                            if(newPlanningUnitTO.getParent().getPlanningUnitElementList() == null){
                                newPlanningUnitTO.setParent(daoFactory.getPlanningUnitDAO().findByID(newPlanningUnitTO.getParent().getId(), true));
                            }
                            for (final PlanningUnitElement planningUnitElementFromParent : newPlanningUnitTO.getParent().getPlanningUnitElementList()) {
                                // Wenn Geschwister-Blaetter vorhanden sind initialisiere mit 00:00:00 fuer alle
                                if(newPlanningUnitTO.getParent().getKindPlanningUnits().size() > 1){
                                    temporaryNewPUEsForPlanningUnit = createNewPlanningUnitElements(ressourceGroups);
                                } else {
                                    // Wenn einziges Blatt uebernehme PUEs vom Vater
                                    final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                                    planningUnitElement.setRessourceGroup(planningUnitElementFromParent.getRessourceGroup());
                                    planningUnitElement.setPlannedMinutes(planningUnitElementFromParent.getPlannedMinutes());
                                    temporaryNewPUEsForPlanningUnit.add(planningUnitElement);
                                }
                            }
                        } else {
                            temporaryNewPUEsForPlanningUnit = createNewPlanningUnitElements(ressourceGroups);
                        }
                    } else {
                        temporaryNewPUEsForPlanningUnit = createNewPlanningUnitElements(ressourceGroups);
                    }
                    for (final PlanningUnitElement newPlanningUnitElement : temporaryNewPUEsForPlanningUnit) {
                        persistedNewPUEsForPlanningUnit.add(daoFactory.getPlanningUnitElementDAO().createEntityFull(newPlanningUnitElement));
                    }
                    daoFactory.getPlanningUnitDAO().addPlanningUnitElementsToPlanningUnit(persistedNewPUEsForPlanningUnit, persistedPlanningUnit);

                    if (newPlanningUnitTO.getParent() == null) {
                        daoFactory.getPlannedProjectDAO().addPlanningUnitToProject(persistedPlanningUnit, projekt);
                        projekt.getTopLevelPlanningUnits().add(persistedPlanningUnit);
                    }
                    AddWindow.this.close();
                    final MainUI ui = screen.getUi();
                    ui.setWorkingArea(new ProjektplanungScreen(ui));
                } catch (final FieldGroup.CommitException e) {
                    logger.warn(e);
                    Notification.show(messages.getString("incompletedata"));
                } catch (final InvalidNameException e) {
                    Notification.show(messages.getString("planning_invalidname"));
                } catch (final SameNameException e) {
                    Notification.show(messages.getString("planning_samename"));
                } catch (MissingNonOptionalPropertyException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (NotYetImplementedException e) {
                    e.printStackTrace();
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

    //Erzeugt neue 00:00:00-PlanningUnitElements für die neue PlanningUnit
    private List<PlanningUnitElement> createNewPlanningUnitElements(final List<RessourceGroup> ressourceGroups) {
        final List<PlanningUnitElement> pues = new ArrayList<>();
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
            planningUnitElement.setPlannedMinutes(0);
            planningUnitElement.setRessourceGroup(ressourceGroup);
            pues.add(planningUnitElement);
        }
        return pues;
    }

    public void show() {
        ui.addWindow(this);
    }
}
