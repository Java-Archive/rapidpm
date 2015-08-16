package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import org.rapidpm.Constants;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.exceptions.SameNameException;

import javax.naming.InvalidNameException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 11.12.12
 * Time: 10:54
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class AddRootPlanningUnitsWindow extends RapidWindow {

    private HorizontalLayout addDeleteLayout = new HorizontalLayout();
    private HorizontalLayout saveCancelLayout = new HorizontalLayout();
    private TextField newPlanningUnitField = new TextField();
    private Button addButton;
    private Button deleteButton;
    private ListSelect planningUnitSelect;
    private Button saveButton;
    private Button cancelButton;
    private BeanItemContainer<PlanningUnit> container;
    private long transientIdCounter;
    private MainUI ui;
    private ResourceBundle messages;
    private PlannedProject project;

    public AddRootPlanningUnitsWindow(final MainUI ui, final ResourceBundle messages) {
        this.ui = ui;
        this.messages = messages;
        setModal(true);
        setCaption(messages.getString("planning_addPlanningUnit"));
        newPlanningUnitField.focus();
        transientIdCounter = 0l;
        project = DaoFactorySingleton.getInstance().getPlannedProjectDAO().findByID(ui.getSession().getAttribute(PlannedProject.class).getId(), true);
        container = new BeanItemContainer<>(PlanningUnit.class);
        addButton = new Button("+");
        deleteButton = new Button("-");
        deleteButton.setEnabled(false);
        saveButton = new Button(messages.getString("save"));
        cancelButton = new Button(messages.getString("cancel"));

        configureListSelect();
        setAddButtonListener();
        setDeleteButtonListener();
        setCancelButtonListener();
        setSaveButtonListener();

        addDeleteLayout.addComponents(newPlanningUnitField, addButton, deleteButton);
        saveCancelLayout.addComponents(saveButton, cancelButton);

        addComponent(addDeleteLayout);
        addComponent(planningUnitSelect);
        addComponent(saveCancelLayout);
    }

    private void setDeleteButtonListener() {
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                container.removeItem((PlanningUnit) planningUnitSelect.getValue());
                deleteButton.setEnabled(false);
            }
        });
    }

    private void setCancelButtonListener() {
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                AddRootPlanningUnitsWindow.this.close();
            }
        });
    }

    private void setSaveButtonListener() {
        saveButton.addClickListener(event -> {
            final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
            final Benutzer notAssignedUser = daoFactory.getBenutzerDAO().loadBenutzerForLogin("<not assigned>");
            final PlanningUnitDAO planningUnitDAO = daoFactory.getPlanningUnitDAO();
            for (PlanningUnit newPlanningUnit : container.getItemIds()) {
                newPlanningUnit.setId(null);
                newPlanningUnit = planningUnitDAO.createEntityFlat(newPlanningUnit);
                planningUnitDAO.addResponsiblePersonToPlanningUnit(notAssignedUser, newPlanningUnit);
                final List<PlanningUnitElement> newPlanningUnitElements = createNewPlanningUnitElements(newPlanningUnit, daoFactory.getRessourceGroupDAO().findAll());
                planningUnitDAO.addPlanningUnitElementsToPlanningUnit(newPlanningUnitElements, newPlanningUnit);
                daoFactory.getPlannedProjectDAO().addPlanningUnitToProject(newPlanningUnit, project);
            }
            AddRootPlanningUnitsWindow.this.close();
            ui.setWorkingArea(new ProjektplanungScreen(ui));
        });

    }

    private void configureListSelect() {
        planningUnitSelect = new ListSelect();
        planningUnitSelect.setContainerDataSource(container);
        planningUnitSelect.setRows(10);
        planningUnitSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        planningUnitSelect.setItemCaptionPropertyId(PlanningUnit.NAME);
        planningUnitSelect.setWidth("100%");
        planningUnitSelect.setMultiSelect(false);
        planningUnitSelect.setImmediate(true);
        planningUnitSelect.setNullSelectionAllowed(false);
        planningUnitSelect.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (event.getProperty().getValue() == null) {
                    deleteButton.setEnabled(false);
                } else {
                    deleteButton.setEnabled(true);
                }
            }
        });
    }

    private void setAddButtonListener() {
        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    final List<PlanningUnit> existingPlanningUnits = project.getPlanningUnits();
                    existingPlanningUnits.addAll(container.getItemIds());
                    final String newPlanningUnitName = newPlanningUnitField.getValue();
                    if (newPlanningUnitName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN)) {
                        throw new InvalidNameException();
                    }
                    for (final PlanningUnit planningUnit : existingPlanningUnits) {
                        if (planningUnit.getPlanningUnitName().equals(newPlanningUnitName)) {
                            throw new SameNameException();
                        }
                    }
                    final PlanningUnit newPlanningUnit = new PlanningUnit();
                    newPlanningUnit.setPlanningUnitName(newPlanningUnitName);
                    newPlanningUnit.setId(String.valueOf(transientIdCounter));
                    container.addBean(newPlanningUnit);
                    transientIdCounter++;
                    newPlanningUnitField.setValue("");
                    newPlanningUnitField.focus();
                } catch (final InvalidNameException e) {
                    Notification.show(messages.getString("planning_invalidname"));
                } catch (final SameNameException e) {
                    Notification.show(messages.getString("planning_samename"));
                }
            }
        });
    }

    //Erzeugt neue 00:00:00-PlanningUnitElements für die neue PlanningUnit und weist diese der PlanningUnit zu.
    private List<PlanningUnitElement> createNewPlanningUnitElements(final PlanningUnit planningUnit,
                                                                    final List<RessourceGroup> ressourceGroups) {
        List<PlanningUnitElement> planningUnitElements = new ArrayList<>();
        planningUnit.setPlanningUnitElementList(new ArrayList<PlanningUnitElement>());
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            PlanningUnitElement planningUnitElement = new PlanningUnitElement();
            planningUnitElement.setPlannedMinutes(0);
            planningUnitElement.setRessourceGroup(ressourceGroup);
            try {
                planningUnitElement = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntityFull(planningUnitElement);
            } catch (InvalidKeyException | NotYetImplementedException | MissingNonOptionalPropertyException e) {
                e.printStackTrace();
            }
            //daoFactory.saveOrUpdateTX(planningUnitElement);
            //planningUnit.getPlanningUnitElementList().add(planningUnitElement);
            planningUnitElements.add(planningUnitElement);
        }
        return planningUnitElements;
    }
}
