package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 17.08.12
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class TreeTableContainerFiller {

    private ProjektBean projektBean;
    private RessourceGroupsBean ressourceGroupsBean;
    private HierarchicalContainer hierarchicalContainer = new HierarchicalContainer();

    public TreeTableContainerFiller(ProjektBean projektBean, RessourceGroupsBean ressourceGroupsBean) {
        this.projektBean = projektBean;
        this.ressourceGroupsBean = ressourceGroupsBean;
    }

    public void fill() {
        final Projekt projekt = projektBean.getProjekt();
        final ArrayList<PlanningUnitGroup> planningUnitGroups = projekt.getPlanningUnitGroups();
        final ArrayList<RessourceGroup> ressourceGroups = ressourceGroupsBean.getRessourceGroups();
        hierarchicalContainer.addContainerProperty("Aufgabe", String.class, null);
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            hierarchicalContainer.addContainerProperty(ressourceGroup.getName(), String.class, "");
        }



        for (final PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
            final Item planningUnitGroupItem = hierarchicalContainer.addItem(planningUnitGroup.getPlanningUnitName());
            planningUnitGroupItem.getItemProperty("Aufgabe").setValue(planningUnitGroup.getPlanningUnitName());
            for (final RessourceGroup ressourceGroup : ressourceGroups) {
                planningUnitGroupItem.getItemProperty(ressourceGroup.getName()).setValue("zu berechnen");
            }
            buildItems(planningUnitGroup.getPlanningUnitList(), planningUnitGroup.getPlanningUnitName());
        }
    }

    private void buildItems(List<PlanningUnit> planningUnits, String parentName)
    {
         for(PlanningUnit planningUnit : planningUnits)
         {
             //---
            final Item planningUnitItem = hierarchicalContainer.addItem(planningUnit.getPlanningUnitElementName());
            planningUnitItem.getItemProperty("Aufgabe").setValue(planningUnit.getPlanningUnitElementName());

            //---
             hierarchicalContainer.setParent( (Object) planningUnit.getPlanningUnitElementName(),(Object)parentName);
             if(planningUnit.getKindPlanningUnits() != null && !planningUnit.getKindPlanningUnits().isEmpty()){
                 for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                     final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
                     planningUnitItem.getItemProperty(planningUnitElementRessourceGroupName).setValue("zu berechnen");
                 }
                 hierarchicalContainer.setChildrenAllowed((Object) planningUnit.getPlanningUnitElementName(),true);
                 buildItems(planningUnit.getKindPlanningUnits(),planningUnit.getPlanningUnitElementName());
             } else{
                 for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                     final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
                     planningUnitItem.getItemProperty(planningUnitElementRessourceGroupName).setValue(parseToTimeString(planningUnitElement));
                 }
                 hierarchicalContainer.setChildrenAllowed((Object) planningUnit.getPlanningUnitElementName(),false);
             }
         }
    }

    private String parseToTimeString(PlanningUnitElement planningUnitElement) {
        Integer days = planningUnitElement.getPlannedDays();
        Integer hours = planningUnitElement.getPlannedHours();
        Integer minutes = planningUnitElement.getPlannedMinutes();
        String daysString;
        String hoursString;
        String minutesString;
        if (days >= 10){
           daysString = String.valueOf(days); 
        } else {
            daysString = "0"+days;
        }

        if (hours >= 10){
            hoursString = String.valueOf(hours);
        } else {
            hoursString = "0"+hours;
        }

        if (minutes >= 10){
            minutesString = String.valueOf(minutes);
        } else {
            minutesString = "0"+minutes;
        }
        return (daysString + ":" +  hoursString + ":" + minutesString);
    }

    public HierarchicalContainer getHierarchicalContainer() {
        return hierarchicalContainer;
    }
}
