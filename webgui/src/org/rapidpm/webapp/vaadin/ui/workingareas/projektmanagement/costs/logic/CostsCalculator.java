package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitElement;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.OldRessourceGroup;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 02.09.12
 * Time: 23:37
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class CostsCalculator {

    private ProjektBean projektBean;

    private final Map<OldRessourceGroup, Double> ressourceGroupsCostsMap = new HashMap<>();
    private ResourceBundle messages;

    private Double totalCostsExakt = 0.0;

    public CostsCalculator(ProjektBean pBean, ResourceBundle bundle) {
        projektBean = pBean;
        messages = bundle;
    }

    public void calculate() {
        calculatePlanningUnitGroupsAndTotalsAbsolut();
        calculateTotalCosts();
    }

    private void calculateTotalCosts() {
        for (final Map.Entry<OldRessourceGroup, Double> ressourceGroupDoubleEntry : ressourceGroupsCostsMap.entrySet()) {
            totalCostsExakt += ressourceGroupDoubleEntry.getValue();
        }
    }


    private void calculatePlanningUnitGroupsAndTotalsAbsolut() {
        final Projekt projekt = projektBean.getProjekt();
        final List<PlanningUnitGroup> planningUnitGroups = projekt.getPlanningUnitGroups();
        for (final PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
            calculatePlanningUnits(planningUnitGroup.getPlanningUnitList());
        }
    }


    private void calculatePlanningUnits(List<PlanningUnit> planningUnits) {
        for (final PlanningUnit planningUnit : planningUnits) {
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                addiereZeileZurRessourceMap(planningUnit);
            } else {
                calculatePlanningUnits(planningUnit.getKindPlanningUnits());
            }
        }
    }

    private void addiereZeileZurRessourceMap(PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            final OldRessourceGroup oldRessourceGroup = planningUnitElement.getOldRessourceGroup();
            if (!oldRessourceGroup.getName().equals(messages.getString("aufgabe"))) {
                final OldRessourceGroup oldRessourceGroup1 = oldRessourceGroup;
                Double costs = getCosts(planningUnitElement);
                if (ressourceGroupsCostsMap.containsKey(oldRessourceGroup1)) {
                    costs += ressourceGroupsCostsMap.get(oldRessourceGroup1);
                }
                ressourceGroupsCostsMap.put(oldRessourceGroup1, costs);
            }
        }
    }

    private Double getCosts(PlanningUnitElement planningUnitElement) {
        final int hoursFromDays = HOURS_DAY * planningUnitElement.getPlannedDays();
        final int hours = planningUnitElement.getPlannedHours();
        final double hoursFromMinutes = STD_ANTEILE * planningUnitElement.getPlannedMinutes();
        final Double totalHours = hoursFromDays + hours + hoursFromMinutes;
        final Double externalEurosPerHour = planningUnitElement.getOldRessourceGroup().getExternalEurosPerHour();
        return totalHours * externalEurosPerHour;
    }

    public Map<OldRessourceGroup, Double> getRessourceGroupsCostsMap() {
        return ressourceGroupsCostsMap;
    }

    public Double getTotalCostsExakt() {
        return totalCostsExakt;
    }

    public String getTotalCostsGerundet() {
        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        return format.format(totalCostsExakt);
    }
}
