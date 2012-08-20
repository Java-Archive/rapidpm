package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 17.08.12
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class TreeTableContainerFiller {

    private ProjektBean container;
    private HierarchicalContainer hierarchicalContainer = new HierarchicalContainer();

    public TreeTableContainerFiller(ProjektBean cont) {
        this.container = cont;

    }

    public void fill() {
        final Projekt projekt = container.getProjekt();
        final ArrayList<PlanningUnitGroup> planningUnitGroups = projekt.getPlanningUnitGroups();
        final ArrayList<RessourceGroup> ressourceGroups = new ArrayList<>();

        for (final PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
            for (final PlanningUnit planningUnit : planningUnitGroup.getPlanningUnitList()) {
                for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                    ressourceGroups.add(planningUnitElement.getRessourceGroup());
                }
            }
        }
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
            for (final PlanningUnit planningUnit : planningUnitGroup.getPlanningUnitList()) {
                final Item planningUnitItem = hierarchicalContainer.addItem(planningUnit.getPlanningUnitElementName());
                planningUnitItem.getItemProperty("Aufgabe").setValue(planningUnit.getPlanningUnitElementName());
                for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                    final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
                    planningUnitItem.getItemProperty(planningUnitElementRessourceGroupName).setValue(parseToTimeString(planningUnitElement));
                }
                hierarchicalContainer.setParent( (Object) planningUnit.getPlanningUnitElementName(),(Object)planningUnitGroup.getPlanningUnitName());
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
