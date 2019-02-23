package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.exceptions.SameNameException;

import javax.naming.InvalidNameException;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

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
    private ListBox<PlanningUnit> planningUnitSelect;
    private Button saveButton;
    private Button cancelButton;
    private List<PlanningUnit> container = new ArrayList<>();
    private long transientIdCounter;
    private ResourceBundle messages;
    private PlannedProject project;

    public AddRootPlanningUnitsWindow(final ResourceBundle messages){
        this.messages = messages;
//        setModal(true);
//        setText(messages.getString("planning_addPlanningUnit"));
        newPlanningUnitField.focus();
        transientIdCounter = 0l;
        project = DaoFactorySingelton.getInstance().getProjectDAO().findByID(VaadinSession.getCurrent().getAttribute(PlannedProject.class).getId());
//        container = new BeanItemContainer<>(PlanningUnit.class);
        addButton = new Button("+");
        saveButton = new Button(messages.getString("save"));
        cancelButton = new Button(messages.getString("cancel"));

        configureListSelect();
        setAddButtonListener();
        setCancelButtonListener();
        setSaveButtonListener();

        addDeleteLayout.add(newPlanningUnitField, addButton);
        saveCancelLayout.add(saveButton, cancelButton);

        add(addDeleteLayout);
        add(planningUnitSelect);
        add(saveCancelLayout);
    }

    private void setCancelButtonListener(){
        cancelButton.addClickListener(
                (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> AddRootPlanningUnitsWindow.this.close());
    }

    private void setSaveButtonListener(){
        saveButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
            daoFactory.new Transaction() {
                @Override
                public void doTask() {
                    final EntityManager entityManager = daoFactory.getEntityManager();
                    final Benutzer notAssignedUser = daoFactory.getBenutzerDAO().findByID(1l);
                    for (final PlanningUnit newPlanningUnit : container) {
                        newPlanningUnit.setId(null);
                        final List<PlanningUnitElement> newPlanningUnitElements = createNewPlanningUnitElements
                                (newPlanningUnit, daoFactory.getRessourceGroupDAO().loadAllEntities());
                        for (PlanningUnitElement newPlanningUnitElement : newPlanningUnitElements) {
                            entityManager.persist(newPlanningUnitElement);
                        }
                        newPlanningUnit.setResponsiblePerson(notAssignedUser);
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
            planningUnitSelect.setItems(project.getPlanningUnits());
            planningUnitSelect.getDataProvider().refreshAll();
        });
    }

    private void configureListSelect() {
        planningUnitSelect = new ListBox<>();
        planningUnitSelect.setItems(container);
        planningUnitSelect.setRenderer(new ComponentRenderer<>(gerateListBoxItem()));
        planningUnitSelect.setWidth("100%");
    }

    private SerializableFunction<PlanningUnit, Component> gerateListBoxItem() {
        return (SerializableFunction<PlanningUnit, Component>) planningUnit -> {
            final Label urlLabel = new Label(planningUnit.getPlanningUnitName());
            final Button button = new Button( new Icon(VaadinIcon.MINUS_CIRCLE), removeButtonClickEvent -> {
                container.remove(planningUnit);
                planningUnitSelect.getDataProvider().refreshAll();
            });
            final Div layout = new Div(urlLabel, button);
            urlLabel.getStyle().set("display", "flex").set("flexDirection", "column").set("marginRight", "10px");
            layout.getStyle().set("display", "flex").set("alignItems", "center");
            return layout;
        };
    }

    private void setAddButtonListener() {
        addButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            try {
                final Set<PlanningUnit> existingPlanningUnits = project.getPlanningUnits();
                existingPlanningUnits.addAll(container);
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
                container.add(newPlanningUnit);
                transientIdCounter++;
                planningUnitSelect.setItems(container);
                newPlanningUnitField.setValue("");
                newPlanningUnitField.focus();
            } catch (final InvalidNameException e) {
                Notification.show(messages.getString("planning_invalidname"));
            } catch (final SameNameException e) {
                Notification.show(messages.getString("planning_samename"));
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
            planningUnitElement.setPlannedMinutes(0);
            planningUnitElement.setRessourceGroup(ressourceGroup);
            //daoFactory.saveOrUpdateTX(planningUnitElement);
            //planningUnit.getPlanningUnitElementList().add(planningUnitElement);
            planningUnitElements.add(planningUnitElement);
        }
        return planningUnitElements;
    }
}
