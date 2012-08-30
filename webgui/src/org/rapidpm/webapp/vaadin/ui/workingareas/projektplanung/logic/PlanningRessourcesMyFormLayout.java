package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.logic;

import com.vaadin.event.MouseEvents;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.components.DaysHoursMinutesFieldValidator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnitElement;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnitGroup;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 11:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningRessourcesMyFormLayout extends MyFormLayout {


    private String planningUnitName;
    private ArrayList<TextField> ressourceGroupFields = new ArrayList<>();

    public PlanningRessourcesMyFormLayout(final PlanningUnitGroup planningUnitGroup, final ProjektplanungScreen screen, final Panel screenPanel) {
        super(planningUnitGroup.getIssueBase(), screen, screenPanel);
        final ArrayList<RessourceGroup> ressourceGroupArrayList = screen.getProjektBean().getProjekt().getRessourceGroups();
        for (RessourceGroup ressourceGroup : ressourceGroupArrayList) {
            buildField(ressourceGroup, planningUnitGroup);
        }
        buildForm();
        for (Object listener : screenPanel.getListeners(MouseEvents.ClickEvent.class)) {
            screenPanel.removeListener((MouseEvents.ClickListener) listener);
        }
    }

    public PlanningRessourcesMyFormLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen, final Panel screenPanel, boolean hasChildren) {
        super(planningUnit.getIssueBase(), screen, screenPanel);
        final ArrayList<RessourceGroup> ressourceGroupArrayList = screen.getProjektBean().getProjekt().getRessourceGroups();
        for (RessourceGroup ressourceGroup : ressourceGroupArrayList) {
            buildField(ressourceGroup, planningUnit);
        }
        buildForm();
        if (hasChildren) {
            for (Object listener : screenPanel.getListeners(MouseEvents.ClickEvent.class)) {
                screenPanel.removeListener((MouseEvents.ClickListener) listener);
            }
        } else {
            cancelButton.addListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    for (TextField textField : ressourceGroupFields) {
                        for (PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
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

                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                while(componentIterator.hasNext()){
                    final Component component = componentIterator.next();
                    if( component instanceof Field){
                        component.setReadOnly(true);
                    }
                }
                buttonLayout.setVisible(false);
            }
        });
        }


    }


    private void buildField(RessourceGroup ressourceGroup, PlanningUnitGroup planningUnitGroup) {
        TextField field = new TextField(ressourceGroup.getName());
        final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
        PlanningUnitElement element = new PlanningUnitElement();
        for (PlanningUnitElement planningUnitElement : planningUnitGroup.getPlanningUnitElementList()) {
            if (planningUnitElement.getRessourceGroup().getName().equals(ressourceGroup.getName())) {
                element = planningUnitElement;
            }
        }
        final PlanningUnitElement planningUnitElement = planningUnitGroup.getPlanningUnitElementList().get(planningUnitGroup.getPlanningUnitElementList().indexOf(element));
        daysHoursMinutesItem.setDays(planningUnitElement.getPlannedDays());
        daysHoursMinutesItem.setHours(planningUnitElement.getPlannedHours());
        daysHoursMinutesItem.setMinutes(planningUnitElement.getPlannedMinutes());
        field.setValue(daysHoursMinutesItem.toString());
        field.setReadOnly(true);
        field.addValidator(new DaysHoursMinutesFieldValidator());
        ressourceGroupFields.add(field);
    }

    private void buildField(RessourceGroup ressourceGroup, PlanningUnit planningUnit) {
        TextField field = new TextField(ressourceGroup.getName());
        final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
        PlanningUnitElement element = new PlanningUnitElement();
        for (PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            if (planningUnitElement.getRessourceGroup().getName().equals(ressourceGroup.getName())) {
                element = planningUnitElement;
            }
        }
        final PlanningUnitElement planningUnitElement = planningUnit.getPlanningUnitElementList().get(planningUnit.getPlanningUnitElementList().indexOf(element));
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
        for (TextField field : ressourceGroupFields) {
            componentsLayout.addComponent(field);
        }
    }
}
