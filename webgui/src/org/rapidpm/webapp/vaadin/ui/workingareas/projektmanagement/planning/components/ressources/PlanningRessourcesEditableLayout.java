package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.ressources;

import com.vaadin.event.MouseEvents;
import com.vaadin.ui.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesFieldValidator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
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

    private List<TextField> ressourceGroupFields = new ArrayList<>();

    public PlanningRessourcesEditableLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                          final Panel screenPanel, boolean hasChildren) {
        super(screen, screenPanel);
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final List<RessourceGroup> ressourceGroups = daoFactory.getRessourceGroupDAO().loadAllEntities();
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
                                DaysHoursMinutesItem item = new DaysHoursMinutesItem();
                                item.setDays(planningUnitElement.getPlannedDays());
                                item.setHours(planningUnitElement.getPlannedHours());
                                item.setMinutes(planningUnitElement.getPlannedMinutes());
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
                    for (final TextField textField : ressourceGroupFields) {
                        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()
                                ) {
                            if (planningUnitElement.getRessourceGroup().getName().equals(textField.getCaption())) {
                                final String[] daysHoursMinutes = textField.getValue().split(":");
                                planningUnitElement.setPlannedDays(Integer.parseInt(daysHoursMinutes[0]));
                                planningUnitElement.setPlannedHours(Integer.parseInt(daysHoursMinutes[1]));
                                planningUnitElement.setPlannedMinutes(Integer.parseInt(daysHoursMinutes[2]));
                                daoFactory.saveOrUpdateTX(planningUnitElement);
                            }
                        }
                    }
                    screen.getUi().setWorkingArea(new ProjektplanungScreen(screen.getUi()));
                }
            });
        }
    }

    private void buildField(final RessourceGroup ressourceGroup, final PlanningUnit planningUnit) {
        final TextField field = new TextField(ressourceGroup.getName());
        final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
        PlanningUnitElement element = new PlanningUnitElement();
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            final String elementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
            if (elementRessourceGroupName.equals(ressourceGroup.getName())) {
                element = planningUnitElement;
            }
        }
        final int index = planningUnit.getPlanningUnitElementList().indexOf(element);
        final PlanningUnitElement planningUnitElement = planningUnit.getPlanningUnitElementList().get(index);
        daysHoursMinutesItem.setDays(planningUnitElement.getPlannedDays());
        daysHoursMinutesItem.setHours(planningUnitElement.getPlannedHours());
        daysHoursMinutesItem.setMinutes(planningUnitElement.getPlannedMinutes());
        field.setValue(daysHoursMinutesItem.toString());
        field.setReadOnly(true);
        field.addValidator(new DaysHoursMinutesFieldValidator());
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