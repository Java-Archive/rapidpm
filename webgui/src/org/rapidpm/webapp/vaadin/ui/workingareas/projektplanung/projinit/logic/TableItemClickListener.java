package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.GridLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnitGroupPlanningUnit;

public class TableItemClickListener implements ItemClickListener {

    private AufwandProjInitScreen screen;
    private PlanningUnitGroupPlanningUnit planningUnitGroupPlanningUnit;

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
        System.out.println(screen.getProjektBean().getProjekt().getPlanningUnitGroups());
        System.out.println(event.getItemId().toString());
        if (!screen.getProjektBean().getProjekt().getPlanningUnitGroupsNames().contains(event.getItemId().toString())) {
            planningUnitGroupPlanningUnit = PlanningUnitGroupPlanningUnit.PLANNING_UNIT;
            for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
                formUnterlayout.addComponent(
                        fieldGroup.buildAndBind(prop));
            }
        } else {
            planningUnitGroupPlanningUnit = PlanningUnitGroupPlanningUnit.PLANNING_UNIT_GROUP;
            for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
                if (prop.equals("Aufgabe"))
                    formUnterlayout.addComponent(
                            fieldGroup.buildAndBind(prop));
            }
        }
        for(Object propertyId : fieldGroup.getBoundPropertyIds())
        {
            System.out.println("propertyId:"+propertyId.toString());
        }
        screen.getSaveButton().addListener(new SaveButtonClickListener(fieldGroup, screen, planningUnitGroupPlanningUnit, event.getItemId()));
        screen.getFormLayout().setVisible(true);

    }

}
