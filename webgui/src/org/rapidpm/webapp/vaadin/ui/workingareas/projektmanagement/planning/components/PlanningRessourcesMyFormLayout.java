package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components;

import com.vaadin.event.MouseEvents;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroup;

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
public class PlanningRessourcesMyFormLayout extends MyFormLayout {

    private List<TextField> ressourceGroupFields = new ArrayList<>();

    public PlanningRessourcesMyFormLayout(final PlanningUnitGroup planningUnitGroup, final ProjektplanungScreen screen,
                                          final Panel screenPanel) {
        super(planningUnitGroup.getIssueBase(), screen, screenPanel);
        final List<RessourceGroup> ressourceGroupArrayList = screen.getProjektBean().getProjekt().getRessourceGroups();
        for (final RessourceGroup ressourceGroup : ressourceGroupArrayList) {
            buildField(ressourceGroup, planningUnitGroup);
        }
        buildForm();
        for (final Object listener : screenPanel.getListeners(MouseEvents.ClickEvent.class)) {
            screenPanel.removeListener((MouseEvents.ClickListener) listener);
        }
    }

    public PlanningRessourcesMyFormLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                          final Panel screenPanel, boolean hasChildren) {
        super(planningUnit.getIssueBase(), screen, screenPanel);
        final List<RessourceGroup> ressourceGroupArrayList = screen.getProjektBean().getProjekt().getRessourceGroups();
        for (final RessourceGroup ressourceGroup : ressourceGroupArrayList) {
            buildField(ressourceGroup, planningUnit);
        }
        buildForm();
        if (hasChildren) {
            for (final Object listener : screenPanel.getListeners(MouseEvents.ClickEvent.class)) {
                screenPanel.removeListener((MouseEvents.ClickListener) listener);
            }
        } else {
            cancelButton.addListener(new Button.ClickListener() {
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
                    final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                    while (componentIterator.hasNext()) {
                        final Component component = componentIterator.next();
                        if (component instanceof Field) {
                            component.setReadOnly(true);
                        }
                    }
                    buttonLayout.setVisible(false);
                }
            });

            saveButton.addListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    for (TextField textField : ressourceGroupFields) {
                        for (PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                            if (planningUnitElement.getRessourceGroup().getName().equals(textField.getCaption())) {
                                final String[] daysHoursMinutes = textField.getValue().split(":");
                                planningUnitElement.setPlannedDays(Integer.parseInt(daysHoursMinutes[0]));
                                planningUnitElement.setPlannedHours(Integer.parseInt(daysHoursMinutes[1]));
                                planningUnitElement.setPlannedMinutes(Integer.parseInt(daysHoursMinutes[2]));
                            }
                        }
                    }
                    final PlanningCalculator calculator = new PlanningCalculator(screen.getProjektBean(),
                            screen.getRessourceGroupsBean());
                    calculator.compute();

                    final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                    while (componentIterator.hasNext()) {
                        final Component component = componentIterator.next();
                        if (component instanceof Field) {
                            component.setReadOnly(true);
                        }
                    }
                    buttonLayout.setVisible(false);
                }
            });
        }


    }


    private void buildField(RessourceGroup ressourceGroup, PlanningUnitGroup planningUnitGroup) {
        final TextField field = new TextField(ressourceGroup.getName());
        final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
        PlanningUnitElement element = new PlanningUnitElement();
        for (final PlanningUnitElement planningUnitElement : planningUnitGroup.getPlanningUnitElementList()) {
            final String elementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
            if (elementRessourceGroupName.equals(ressourceGroup.getName())) {
                element = planningUnitElement;
            }
        }
        final int index = planningUnitGroup.getPlanningUnitElementList().indexOf(element);
        final PlanningUnitElement planningUnitElement = planningUnitGroup.getPlanningUnitElementList().get(index);
        daysHoursMinutesItem.setDays(planningUnitElement.getPlannedDays());
        daysHoursMinutesItem.setHours(planningUnitElement.getPlannedHours());
        daysHoursMinutesItem.setMinutes(planningUnitElement.getPlannedMinutes());
        field.setValue(daysHoursMinutesItem.toString());
        field.setReadOnly(true);
        field.addValidator(new DaysHoursMinutesFieldValidator());
        ressourceGroupFields.add(field);
    }

    private void buildField(RessourceGroup ressourceGroup, PlanningUnit planningUnit) {
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
}
