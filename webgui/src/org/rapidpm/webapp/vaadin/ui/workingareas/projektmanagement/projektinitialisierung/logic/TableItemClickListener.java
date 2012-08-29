package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektinitialisierung.logic;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.GridLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektinitialisierung.ProjektinitialisierungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektinitialisierung.components.DaysHoursMinutesFieldValidator;
import org.rapidpm.transience.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.transience.prj.projectmanagement.planning.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektinitialisierung.datenmodell.KnotenBlatt;

import java.util.ArrayList;
import java.util.List;

public class TableItemClickListener implements ItemClickListener {

    private ProjektinitialisierungScreen screen;
    private KnotenBlatt knotenBlatt;

    private PlanningUnit foundPlanningUnit = null;

    public TableItemClickListener(ProjektinitialisierungScreen screen) {
        this.screen = screen;
    }

    @Override
    public void itemClick(ItemClickEvent event) {
        final GridLayout formUnterlayout = screen.getUpperFormLayout();
        final FieldGroup fieldGroup = new FieldGroup(event.getItem());
        for (final Object listener : screen.getSaveButton().getListeners(Event.class)) {
            if (listener instanceof ClickListener) {
                screen.getSaveButton().removeListener((ClickListener) listener);
            }

        }
        formUnterlayout.removeAllComponents();
        final Object itemId = event.getItemId();
        final String aufgabe = screen.getDataSource().getItem(itemId).getItemProperty("Aufgabe").getValue().toString();
        final ArrayList<String> planningUnitGroupsNames = screen.getProjektBean().getProjekt().getPlanningUnitGroupsNames();

        foundPlanningUnit = null;
        if (planningUnitGroupsNames.contains(aufgabe)) {
            knotenBlatt = KnotenBlatt.PLANNING_UNIT_GROUP;
            for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
                if (prop.equals("Aufgabe"))
                    formUnterlayout.addComponent(
                            fieldGroup.buildAndBind(prop));
            }
        } else {
            final ArrayList<PlanningUnitGroup> planningUnitGroups = screen.getProjektBean().getProjekt().getPlanningUnitGroups();
            //PlanningUnit planningUnit = null;
            for(final PlanningUnitGroup planningUnitGroup : planningUnitGroups){
                if(foundPlanningUnit == null){
                    getPlanningUnit(planningUnitGroup.getPlanningUnitList(), itemId.toString());
                }
            }
            if (foundPlanningUnit.getKindPlanningUnits() != null && !foundPlanningUnit.getKindPlanningUnits().isEmpty()) {
                knotenBlatt = KnotenBlatt.PLANNING_UNIT_KNOTEN;
                for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
                    if (prop.equals("Aufgabe"))
                        formUnterlayout.addComponent(
                                fieldGroup.buildAndBind(prop));
                }
                for(final Object propertyId : fieldGroup.getBoundPropertyIds())
                {
                    fieldGroup.getField(propertyId).setRequired(true);
                }
            } else {
                knotenBlatt = KnotenBlatt.PLANNING_UNIT_BLATT;
                for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
                    formUnterlayout.addComponent(
                            fieldGroup.buildAndBind(prop));
                }
                for(final Object propertyId : fieldGroup.getBoundPropertyIds())
                {
                    if(!propertyId.equals("Aufgabe")){
                        fieldGroup.getField(propertyId).addValidator(new DaysHoursMinutesFieldValidator());
                    }
                    fieldGroup.getField(propertyId).setRequired(true);
                }
            }
        }

        screen.getSaveButton().addListener(new SaveButtonClickListener(fieldGroup, screen, knotenBlatt, itemId));
        screen.getFormLayout().setVisible(true);
    }

    private void getPlanningUnit(List<PlanningUnit> planningUnits, String itemId) {
        for (PlanningUnit planningUnit : planningUnits) {
            if(planningUnit.getPlanningUnitName().equals(itemId)){
                foundPlanningUnit = planningUnit;
            } else {
                if(planningUnit.getKindPlanningUnits() != null && !planningUnit.getKindPlanningUnits().isEmpty() ){
                    getPlanningUnit(planningUnit.getKindPlanningUnits(), itemId);
                }
            }
        }
    }



}
