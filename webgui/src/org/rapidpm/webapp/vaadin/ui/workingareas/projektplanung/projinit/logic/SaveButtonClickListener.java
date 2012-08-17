package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnitElement;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnitGroupPlanningUnit;

public class SaveButtonClickListener implements ClickListener {
    private FieldGroup fieldGroup;
    private AufwandProjInitScreen screen;
    private PlanningUnitGroupPlanningUnit planningUnitGroupPlanningUnit;
    private Object itemId;

    public SaveButtonClickListener(FieldGroup fieldGroup, AufwandProjInitScreen screen, PlanningUnitGroupPlanningUnit planningUnitGroupPlanningUnit, Object itemId) {
        this.fieldGroup = fieldGroup;
        this.screen = screen;
        this.planningUnitGroupPlanningUnit = planningUnitGroupPlanningUnit;
        this.itemId = itemId;
    }

    @Override
    public void buttonClick(ClickEvent event) {


        final ProjInitComputer computer = new ProjInitComputer(screen);
        try {
            fieldGroup.commit();
            screen.getTreeTable().setValue(null);    //null selection


            if (planningUnitGroupPlanningUnit.equals(PlanningUnitGroupPlanningUnit.PLANNING_UNIT_GROUP)) {
                for(PlanningUnitGroup planningUnitGroup : screen.getProjektBean().getProjekt().getPlanningUnitGroups()){
                    if(planningUnitGroup.getPlanningUnitName().equals(itemId)){
                        planningUnitGroup.setPlanningUnitName(screen.getDataSource().getItem(itemId).getItemProperty("Aufgabe").getValue().toString());
                    }
                }

            }else{
                for(PlanningUnitGroup planningUnitGroup : screen.getProjektBean().getProjekt().getPlanningUnitGroups()){
                    for(PlanningUnit planningUnit : planningUnitGroup.getPlanningUnitList()){
                        if(planningUnit.getPlanningUnitElementName().equals(itemId)){
                            planningUnit.setPlanningUnitElementName(screen.getDataSource().getItem(itemId).getItemProperty("Aufgabe").getValue().toString());
                            for(PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList())
                            {
                                final String daysHoursMinutesString = screen.getDataSource().getItem(itemId).getItemProperty(planningUnitElement.getRessourceGroup().getName()).getValue().toString();
                                String[] daysHoursMinutes = daysHoursMinutesString.split(":");
                                planningUnitElement.setPlannedDays(Integer.parseInt(daysHoursMinutes[0]));
                                planningUnitElement.setPlannedHours(Integer.parseInt(daysHoursMinutes[1]));
                                planningUnitElement.setPlannedMinutes(Integer.parseInt(daysHoursMinutes[2]));
                            }


                        }
                    }

                }
                computer.compute();
                computer.setValuesInScreen();
            }
            screen.getFormLayout().setVisible(false);

            for(PlanningUnitGroup group : screen.getProjektBean().getProjekt().getPlanningUnitGroups())
            {
                System.out.println("a: "+group.getPlanningUnitName());
            }
        } catch (CommitException e) {
            //tue nichts falls commit nicht erfolgreich war
        }
    }

}
