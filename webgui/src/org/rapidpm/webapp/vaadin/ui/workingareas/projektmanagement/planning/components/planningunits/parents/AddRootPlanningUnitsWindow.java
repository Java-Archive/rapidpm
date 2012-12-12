package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.exceptions.SameNameException;

import javax.naming.InvalidNameException;
import javax.persistence.EntityManager;
import java.util.*;

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

    public AddRootPlanningUnitsWindow(final MainUI ui, final ResourceBundle messages){
        this.ui = ui;
        this.messages = messages;
        setModal(true);
        transientIdCounter = 0l;
        project = DaoFactorySingelton.getInstance().getProjectDAO().findByID(ui.getSession().getAttribute(PlannedProject.class).getId());
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

        addDeleteLayout.addComponents(newPlanningUnitField, addButton,deleteButton);
        saveCancelLayout.addComponents(saveButton, cancelButton);

        addComponent(addDeleteLayout);
        addComponent(planningUnitSelect);
        addComponent(saveCancelLayout);
    }

    private void setDeleteButtonListener() {
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                container.removeItem((PlanningUnit)planningUnitSelect.getValue());
                deleteButton.setEnabled(false);
            }
        });
    }

    private void setCancelButtonListener(){
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                AddRootPlanningUnitsWindow.this.close();
            }
        });
    }

    private void setSaveButtonListener(){
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
                daoFactory.new Transaction() {
                    @Override
                    public void doTask() {
                        final EntityManager entityManager = daoFactory.getEntityManager();
                        for (final PlanningUnit newPlanningUnit : container.getItemIds()) {
                            newPlanningUnit.setId(null);
                            final List<PlanningUnitElement> newPlanningUnitElements = createNewPlanningUnitElements
                                    (newPlanningUnit, daoFactory.getRessourceGroupDAO().loadAllEntities());
                            for (PlanningUnitElement newPlanningUnitElement : newPlanningUnitElements) {
                                entityManager.persist(newPlanningUnitElement);
                            }
                            newPlanningUnit.setPlanningUnitElementList(newPlanningUnitElements);
                            entityManager.persist(newPlanningUnit);
                            project.getPlanningUnits().add(newPlanningUnit);
                        }
                        entityManager.merge(project);
                        entityManager.flush();
                        entityManager.refresh(project);
                    }
                }.execute();
                AddRootPlanningUnitsWindow.this.close();
                ui.setWorkingArea(new ProjektplanungScreen(ui));
            }
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
                if(event.getProperty().getValue() == null){
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
                    final Set<PlanningUnit> existingPlanningUnits = project.getPlanningUnits();
                    existingPlanningUnits.addAll(container.getItemIds());
                    final String newPlanningUnitName = newPlanningUnitField.getValue();
                    if(newPlanningUnitName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN)){
                        throw new InvalidNameException();
                    }
                    for (final PlanningUnit planningUnit : existingPlanningUnits) {
                        if(planningUnit.getPlanningUnitName().equals(newPlanningUnitName)){
                            throw new SameNameException();
                        }
                    }
                    final PlanningUnit newPlanningUnit = new PlanningUnit();
                    newPlanningUnit.setPlanningUnitName(newPlanningUnitName);
                    newPlanningUnit.setId(transientIdCounter);
                    container.addBean(newPlanningUnit);
                    transientIdCounter++;
                    newPlanningUnitField.setValue("");
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
            final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
            planningUnitElement.setPlannedDays(0);
            planningUnitElement.setPlannedHours(0);
            planningUnitElement.setPlannedMinutes(0);
            planningUnitElement.setRessourceGroup(ressourceGroup);
            //daoFactory.saveOrUpdateTX(planningUnitElement);
            //planningUnit.getPlanningUnitElementList().add(planningUnitElement);
            planningUnitElements.add(planningUnitElement);
        }
        return planningUnitElements;
    }
}
