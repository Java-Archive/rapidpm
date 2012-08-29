package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektinitialisierung.logic;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.transience.prj.projectmanagement.planning.*;
import org.rapidpm.transience.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.transience.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupsBean;

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
            final Item planningUnitGroupItem = hierarchicalContainer.addItem(planningUnitGroup.getPlanningUnitGroupName());
            planningUnitGroupItem.getItemProperty("Aufgabe").setValue(planningUnitGroup.getPlanningUnitGroupName());
            for (final RessourceGroup ressourceGroup : ressourceGroups) {
                planningUnitGroupItem.getItemProperty(ressourceGroup.getName()).setValue("zu berechnen");
            }
            buildItems(planningUnitGroup.getPlanningUnitList(), planningUnitGroup.getPlanningUnitGroupName());


//
//
//
//            for (final PlanningUnit planningUnit : planningUnitGroup.getPlanningUnitList()) {
//                final Item planningUnitItem = hierarchicalContainer.addItem(planningUnit.getPlanningUnitGroupName());
//                planningUnitItem.getItemProperty("Aufgabe").setValue(planningUnit.getPlanningUnitGroupName());
//                for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
//                    final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
//                    planningUnitItem.getItemProperty(planningUnitElementRessourceGroupName).setValue(parseToTimeString(planningUnitElement));
//                }
//                hierarchicalContainer.setParent( (Object) planningUnit.getPlanningUnitGroupName(),(Object)planningUnitGroup.getPlanningUnitGroupName());
//                hierarchicalContainer.setChildrenAllowed((Object) planningUnit.getPlanningUnitGroupName(),false);
//            }
        }
    }

    private void buildItems(List<PlanningUnit> planningUnits, String parentName)
    {
         for(PlanningUnit planningUnit : planningUnits)
         {
             //---
            final Item planningUnitItem = hierarchicalContainer.addItem(planningUnit.getPlanningUnitName());
            planningUnitItem.getItemProperty("Aufgabe").setValue(planningUnit.getPlanningUnitName());

            //---
             hierarchicalContainer.setParent( (Object) planningUnit.getPlanningUnitName(),(Object)parentName);
             if(planningUnit.getKindPlanningUnits() != null && !planningUnit.getKindPlanningUnits().isEmpty()){
                 for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                     final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
                     planningUnitItem.getItemProperty(planningUnitElementRessourceGroupName).setValue("zu berechnen");
                 }
                 hierarchicalContainer.setChildrenAllowed((Object) planningUnit.getPlanningUnitName(),true);
                 buildItems(planningUnit.getKindPlanningUnits(),planningUnit.getPlanningUnitName());
             } else{
                 for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                     final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
                     planningUnitItem.getItemProperty(planningUnitElementRessourceGroupName).setValue(parseToTimeString(planningUnitElement));
                 }
                 hierarchicalContainer.setChildrenAllowed((Object) planningUnit.getPlanningUnitName(),false);
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
