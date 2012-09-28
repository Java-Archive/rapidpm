package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

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


    private final Map<RessourceGroup, Double> ressourceGroupsCostsMap = new HashMap<>();
    private ResourceBundle messages;
    private CostsCalcutorBean bean;

    private Double totalCostsExakt = 0.0;

    public CostsCalculator(ResourceBundle bundle) {
        bean = EJBFactory.getEjbInstance(CostsCalcutorBean.class);
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
        //final Integer currentProjectIndex = bean.getCurrentProjectIndex();
        final PlannedProject projekt = bean.getDaoFactoryBean().getPlannedProjectDAO().loadAllEntities().get(0);
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
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
            if (!ressourceGroup.getName().equals(messages.getString("aufgabe"))) {
                final RessourceGroup ressourceGroup1 = ressourceGroup;
                Double costs = getCosts(planningUnitElement);
                if (ressourceGroupsCostsMap.containsKey(ressourceGroup1)) {
                    costs += ressourceGroupsCostsMap.get(ressourceGroup1);
                }
                ressourceGroupsCostsMap.put(ressourceGroup1, costs);
            }
        }
    }

    private Double getCosts(PlanningUnitElement planningUnitElement) {
        final int hoursFromDays = HOURS_DAY * planningUnitElement.getPlannedDays();
        final int hours = planningUnitElement.getPlannedHours();
        final double hoursFromMinutes = STD_ANTEILE * planningUnitElement.getPlannedMinutes();
        final Double totalHours = hoursFromDays + hours + hoursFromMinutes;
        final Double externalEurosPerHour = planningUnitElement.getRessourceGroup().getExternalEurosPerHour();
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
