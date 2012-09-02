package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.logic;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 31.08.12
 * Time: 16:29
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableDataSourceFiller {

    private RessourceGroupsBean ressourceGroupsBean;
    private ProjektBean projektBean;
    private ArrayList<RessourceGroup> ressourceGroups;
    private final Map<RessourceGroup, Double> ressourceGroupsCostsMap = new HashMap<>();

    private HierarchicalContainer dataSource;

    public TreeTableDataSourceFiller(RessourceGroupsBean rBean, ProjektBean pBean, HierarchicalContainer dSource){
        ressourceGroupsBean = rBean;
        projektBean = pBean;
        dataSource = dSource;
        ressourceGroups = ressourceGroupsBean.getRessourceGroups();

        dataSource.removeAllItems();
        dataSource.addContainerProperty("Aufgabe", String.class, null);
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            dataSource.addContainerProperty(ressourceGroup.getName(), Double.class, "");
        }



    }

    public void fill(){
        computePlanningUnitGroupsAndTotalsAbsolut();
    }

    private void computePlanningUnitGroupsAndTotalsAbsolut() {
        for (final PlanningUnitGroup planningUnitGroup : projektBean.getProjekt().getPlanningUnitGroups()) {
            final String planningUnitGroupName = planningUnitGroup.getPlanningUnitName();
            final Item planningUnitGroupItem = dataSource.addItem(planningUnitGroupName);
            planningUnitGroupItem.getItemProperty("Aufgabe").setValue(planningUnitGroupName);
            if (planningUnitGroup.getPlanningUnitList() == null || planningUnitGroup.getPlanningUnitList().isEmpty()) {
                for (final RessourceGroup spalte : ressourceGroups) {
                    for(PlanningUnitElement planningUnitElement : planningUnitGroup.getPlanningUnitElementList()){
                        if(planningUnitElement.getRessourceGroup().equals(spalte)){
                            planningUnitGroupItem.getItemProperty(spalte.getName()).setValue(0.0);
                        }
                    }
                }
            } else {
                computePlanningUnits(planningUnitGroup.getPlanningUnitList(), planningUnitGroupName);
            }
        }
    }


    private void computePlanningUnits(List<PlanningUnit> planningUnits, String parent) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final String planningUnitName = planningUnit.getPlanningUnitElementName();
            final Item planningUnitItem = dataSource.addItem(planningUnitName);
            planningUnitItem.getItemProperty("Aufgabe").setValue(planningUnitName);
            dataSource.setParent(planningUnitName, parent);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                for(final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()){
                    final Double costs = getCosts(planningUnitElement);
                    planningUnitItem.getItemProperty(planningUnitElement.getRessourceGroup().getName()).setValue(costs);
                }
                addiereZeileZurRessourceMap(planningUnit);
            } else {
                computePlanningUnits(planningUnit.getKindPlanningUnits(), planningUnitName);
            }
        }
        for (RessourceGroup spalte : ressourceGroups) {
            dataSource.getItem(parent).getItemProperty(spalte.getName()).setValue(ressourceGroupsCostsMap.get(spalte));
        }
    }

    private void addiereZeileZurRessourceMap(PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            if (!planningUnitElement.getRessourceGroup().getName().equals("Aufgabe")) {
                final RessourceGroup ressourceGroup1 = planningUnitElement.getRessourceGroup();
                Double costs;
                costs = getCosts(planningUnitElement);
                if (ressourceGroupsCostsMap.containsKey(ressourceGroup1)) {
                    costs += ressourceGroupsCostsMap.get(ressourceGroup1);
                }
                ressourceGroupsCostsMap.put(ressourceGroup1, costs);
            }
        }
    }

    private Double getCosts(PlanningUnitElement planningUnitElement) {
        Double hours = (24.0*planningUnitElement.getPlannedDays())
                +planningUnitElement.getPlannedHours()
                +(1.0/60.0*planningUnitElement.getPlannedMinutes());
        return hours * planningUnitElement.getRessourceGroup().getExternalEurosPerHour();
    }

}
