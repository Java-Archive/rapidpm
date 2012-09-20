package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

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

    private final Map<RessourceGroup, Double> ressourceGroupsCostsMap = new HashMap<>();
    private ResourceBundle messages;

    private Double totalCostsExakt = 0.0;

    public CostsCalculator(ProjektBean pBean, ResourceBundle bundle) {
        projektBean = pBean;
        messages = bundle;
    }

    public void calculate() {
        calculatePlanningUnitsAndTotalsAbsolut();
        calculateTotalCosts();
    }

    private void calculateTotalCosts() {
        for (final Map.Entry<RessourceGroup, Double> ressourceGroupDoubleEntry : ressourceGroupsCostsMap.entrySet()) {
            totalCostsExakt += ressourceGroupDoubleEntry.getValue();
        }
    }


    private void calculatePlanningUnitsAndTotalsAbsolut() {
        final Integer currentProjectIndex = projektBean.getCurrentProjectIndex();
        final Projekt projekt = projektBean.getProjekte().get(currentProjectIndex);
        final List<PlanningUnit> planningUnits = projekt.getPlanningUnits();
        for (final PlanningUnit planningUnit : planningUnits) {
            calculatePlanningUnits(planningUnit.getKindPlanningUnits());
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
        for (final PlanningUnitElement oldPlanningUnitElement : planningUnit.getPlanningUnitElementList()) {
            final RessourceGroup oldRessourceGroup = oldPlanningUnitElement.getRessourceGroup();
            if (!oldRessourceGroup.getName().equals(messages.getString("aufgabe"))) {
                final RessourceGroup oldRessourceGroup1 = oldRessourceGroup;
                Double costs = getCosts(oldPlanningUnitElement);
                if (ressourceGroupsCostsMap.containsKey(oldRessourceGroup1)) {
                    costs += ressourceGroupsCostsMap.get(oldRessourceGroup1);
                }
                ressourceGroupsCostsMap.put(oldRessourceGroup1, costs);
            }
        }
    }

    private Double getCosts(PlanningUnitElement oldPlanningUnitElement) {
        final int hoursFromDays = HOURS_DAY * oldPlanningUnitElement.getPlannedDays();
        final int hours = oldPlanningUnitElement.getPlannedHours();
        final double hoursFromMinutes = STD_ANTEILE * oldPlanningUnitElement.getPlannedMinutes();
        final Double totalHours = hoursFromDays + hours + hoursFromMinutes;
        final Double externalEurosPerHour = oldPlanningUnitElement.getRessourceGroup().getExternalEurosPerHour();
        return totalHours * externalEurosPerHour;
    }

    public Map<RessourceGroup, Double> getRessourceGroupsCostsMap() {
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
