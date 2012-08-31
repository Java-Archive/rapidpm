package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.logic;

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
            final Item planningUnitElementItem = hierarchicalContainer.addItem(planningUnit.getPlanningUnitElementName());
            planningUnitElementItem.getItemProperty("Aufgabe").setValue(planningUnit.getPlanningUnitElementName());

            //---
             hierarchicalContainer.setParent( (Object) planningUnit.getPlanningUnitElementName(),(Object)parentName);
             if(planningUnit.getKindPlanningUnits() != null && !planningUnit.getKindPlanningUnits().isEmpty()){
                 for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                     final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
                     planningUnitElementItem.getItemProperty(planningUnitElementRessourceGroupName).setValue("zu berechnen");
                 }
                 hierarchicalContainer.setChildrenAllowed((Object) planningUnit.getPlanningUnitElementName(),true);
                 buildItems(planningUnit.getKindPlanningUnits(),planningUnit.getPlanningUnitElementName());
             } else{
                 for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                     final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
                     RessourceGroup ressourceGroup1 = null;
                    for(RessourceGroup ressourceGroup : ressourceGroupsBean.getRessourceGroups()){
                        if(ressourceGroup.equals(planningUnitElement.getRessourceGroup()))
                        {
                           ressourceGroup1 = ressourceGroup;
                        }
                    }

                     final String kostenString = ((Double) (getHours(planningUnitElement) * ressourceGroup1.getExternalEurosPerHour())).toString();
                     planningUnitElementItem.getItemProperty(planningUnitElementRessourceGroupName).setValue(kostenString);
                 }
                 hierarchicalContainer.setChildrenAllowed((Object) planningUnit.getPlanningUnitElementName(),false);
             }
         }
    }

    private Double getHours(PlanningUnitElement planningUnitElement) {
        Double hours = (24.0*planningUnitElement.getPlannedDays())
                +planningUnitElement.getPlannedHours()
                +(100.0/60.0*planningUnitElement.getPlannedMinutes());
        return hours;
    }

    public HierarchicalContainer getHierarchicalContainer() {
        return hierarchicalContainer;
    }
}
