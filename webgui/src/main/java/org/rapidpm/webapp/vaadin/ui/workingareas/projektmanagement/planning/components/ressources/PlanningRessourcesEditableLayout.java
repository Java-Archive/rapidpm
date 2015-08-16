package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.ressources;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesFieldValidator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 11:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningRessourcesEditableLayout extends EditableLayout {

    private final ProjektplanungScreen screen;
    private List<TextField> ressourceGroupFields = new ArrayList<>();

    public PlanningRessourcesEditableLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                          final Panel screenPanel, boolean hasChildren) {
        super(screen, screenPanel);
        this.screen = screen;
        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
        final List<RessourceGroup> ressourceGroups = daoFactory.getRessourceGroupDAO().findAll();
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            buildField(ressourceGroup, planningUnit);
        }
        buildForm();
        if (hasChildren) {
            for (final Object listener : screenPanel.getListeners(MouseEvents.ClickEvent.class)) {
                screenPanel.removeClickListener((MouseEvents.ClickListener) listener);
            }
        } else {
            cancelButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    for (final TextField textField : ressourceGroupFields) {
                        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                            if (planningUnitElement.getRessourceGroup().getName().equals(textField.getCaption())) {
                                final VaadinSession session = screen.getUi().getSession();
                                final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
                                final DaysHoursMinutesItem item = new DaysHoursMinutesItem(planningUnitElement,
                                        currentProject.getHoursPerWorkingDay());
                                textField.setValue(item.toString());
                            }
                        }
                    }
                    final Iterator<Component> componentIterator = componentsLayout.iterator();
                    while (componentIterator.hasNext()) {
                        final Component component = componentIterator.next();
                        if (component instanceof Field) {
                            component.setReadOnly(true);
                        }
                    }
                    buttonLayout.setVisible(false);
                }
            });

            saveButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    boolean allValid = true;
                    for (final TextField textField : ressourceGroupFields) {
                        if (!textField.isValid()){
                            allValid = false;
                        }
                    }
                    if(allValid){
                        for (final TextField textField : ressourceGroupFields) {
                            for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()
                                    ) {
                                if (planningUnitElement.getRessourceGroup().getName().equals(textField.getCaption())) {
                                    final VaadinSession session = screen.getUi().getSession();
                                    final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
                                    final DaysHoursMinutesItem item = new DaysHoursMinutesItem(textField.getValue(),
                                            currentProject.getHoursPerWorkingDay());
                                    planningUnitElement.setPlannedMinutes(item.getMinutesFromDaysHoursMinutes());
//                                    daoFactory.saveOrUpdateTX(planningUnitElement);
                                }
                            }
                        }
                        screen.getUi().setWorkingArea(new ProjektplanungScreen(screen.getUi()));
                    }
                }
            });
        }
    }

    private void buildField(final RessourceGroup ressourceGroup, final PlanningUnit planningUnit) {
        final TextField field = new TextField(ressourceGroup.getName());
        PlanningUnitElement element = new PlanningUnitElement();
        for (PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            planningUnitElement = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().findByID(planningUnitElement.getId(), true);
            final String elementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
            if (elementRessourceGroupName.equals(ressourceGroup.getName())) {
                element = planningUnitElement;
            }
        }
        final int index = planningUnit.getPlanningUnitElementList().indexOf(element);
        final PlanningUnitElement planningUnitElement = planningUnit.getPlanningUnitElementList().get(index);
        final VaadinSession session = screen.getUi().getSession();
        final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
        final DaysHoursMinutesItem item = new DaysHoursMinutesItem(planningUnitElement,
                currentProject.getHoursPerWorkingDay());
        field.setValue(item.toString());
        field.setReadOnly(true);
        field.addValidator(new DaysHoursMinutesFieldValidator(screen));
        ressourceGroupFields.add(field);
    }

    @Override
    protected void buildForm() {
        for (final TextField field : ressourceGroupFields) {
            componentsLayout.addComponent(field);
        }
    }

    @Override
    protected void setLayout() {
        componentsLayout = new FormLayout();
    }
}