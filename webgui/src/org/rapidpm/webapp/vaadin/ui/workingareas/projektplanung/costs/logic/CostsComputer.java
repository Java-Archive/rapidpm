package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.logic;

import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnitElement;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.ProjektBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 02.09.12
 * Time: 23:37
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class CostsComputer {

    private ProjektBean projektBean;

    private final Map<RessourceGroup, Double> ressourceGroupsCostsMap = new HashMap<>();

    public CostsComputer(ProjektBean pBean){
        projektBean = pBean;
    }

    public void compute(){
        computePlanningUnitGroupsAndTotalsAbsolut();
    }


    private void computePlanningUnitGroupsAndTotalsAbsolut() {
        for (final PlanningUnitGroup planningUnitGroup : projektBean.getProjekt().getPlanningUnitGroups()) {
                computePlanningUnits(planningUnitGroup.getPlanningUnitList());

        }
    }


    private void computePlanningUnits(List<PlanningUnit> planningUnits) {
        for (PlanningUnit planningUnit : planningUnits) {
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                addiereZeileZurRessourceMap(planningUnit);
            } else {
                computePlanningUnits(planningUnit.getKindPlanningUnits());
            }
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

    public Map<RessourceGroup, Double> getRessourceGroupsCostsMap() {
        return ressourceGroupsCostsMap;
    }
}
