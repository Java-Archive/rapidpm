package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.GridLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesFieldValidator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.datenmodell.KnotenBlattEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;

import java.util.List;

import static org.rapidpm.Constants.*;

public class TableItemClickListener implements ItemClickListener {

    private AufwandProjInitScreen screen;
    private KnotenBlattEnum knotenBlattEnum;

    private PlanningUnit foundPlanningUnit = null;

    public TableItemClickListener(AufwandProjInitScreen screen) {
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
        final String aufgabe = screen.getDataSource().getItem(itemId).getItemProperty(AUFGABE_SPALTE).getValue().toString();
        final List<String> planningUnitGroupsNames = screen.getProjektBean().getProjekt().getPlanningUnitGroupsNames();

        foundPlanningUnit = null;
        if (planningUnitGroupsNames.contains(aufgabe)) {
            knotenBlattEnum = KnotenBlattEnum.PLANNING_UNIT_GROUP;
            for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
                if (prop.equals(AUFGABE_SPALTE))
                    formUnterlayout.addComponent(
                            fieldGroup.buildAndBind(prop));
            }
        } else {
            final List<PlanningUnitGroup> planningUnitGroups = screen.getProjektBean().getProjekt().getPlanningUnitGroups();
            //PlanningUnit planningUnit = null;
            for(final PlanningUnitGroup planningUnitGroup : planningUnitGroups){
                if(foundPlanningUnit == null){
                    getPlanningUnit(planningUnitGroup.getPlanningUnitList(), itemId.toString());
                }
            }
            if (foundPlanningUnit.getKindPlanningUnits() != null && !foundPlanningUnit.getKindPlanningUnits().isEmpty()) {
                knotenBlattEnum = KnotenBlattEnum.PLANNING_UNIT_KNOTEN;
                for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
                    if (prop.equals(AUFGABE_SPALTE))
                        formUnterlayout.addComponent(
                                fieldGroup.buildAndBind(prop));
                }
                for(final Object propertyId : fieldGroup.getBoundPropertyIds())
                {
                    fieldGroup.getField(propertyId).setRequired(true);
                }
            } else {
                knotenBlattEnum = KnotenBlattEnum.PLANNING_UNIT_BLATT;
                for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
                    formUnterlayout.addComponent(
                            fieldGroup.buildAndBind(prop));
                }
                for(final Object propertyId : fieldGroup.getBoundPropertyIds())
                {
                    if(!propertyId.equals(AUFGABE_SPALTE)){
                        fieldGroup.getField(propertyId).addValidator(new DaysHoursMinutesFieldValidator());
                    }
                    fieldGroup.getField(propertyId).setRequired(true);
                }
            }
        }

        screen.getSaveButton().addListener(new SaveButtonClickListener(fieldGroup, screen, knotenBlattEnum, itemId));
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
