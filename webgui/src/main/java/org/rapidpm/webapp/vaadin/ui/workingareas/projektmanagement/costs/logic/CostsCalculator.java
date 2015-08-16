package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.DECIMAL_FORMAT;
import static org.rapidpm.Constants.MINS_HOUR;

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
//    private CostsCalcutorBean bean;
    private PlannedProject projekt;

    private Double totalCostsExakt = 0.0;

    public CostsCalculator(MainUI ui, final ResourceBundle bundle) {
//        bean = EJBFactory.getEjbInstance(CostsCalcutorBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
        final PlannedProject projectFromSession = ui.getSession().getAttribute(PlannedProject.class);
        projekt = daoFactory.getPlannedProjectDAO().findByID(projectFromSession.getId(), true);
//        daoFactory.getEntityManager().refresh(projekt);
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
        projekt = DaoFactorySingleton.getInstance().getPlannedProjectDAO().findByID(projekt.getId(), true);
        final List<PlanningUnit> planningUnits = projekt.getPlanningUnits();
        for (PlanningUnit planningUnit : planningUnits) {
            planningUnit = DaoFactorySingleton.getInstance().getPlanningUnitDAO().findByID(planningUnit.getId(), true);
            calculatePlanningUnits(planningUnit, planningUnit.getKindPlanningUnits());
        }
    }


    private void calculatePlanningUnits(final PlanningUnit parentPlanningUnit, final List<PlanningUnit> planningUnits) {
        if(planningUnits == null || planningUnits.isEmpty()){
            addiereZeileZurRessourceMap(parentPlanningUnit);
        } else {
            for (PlanningUnit planningUnit : planningUnits) {
                planningUnit = DaoFactorySingleton.getInstance().getPlanningUnitDAO().findByID(planningUnit.getId(), true);
                if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                    addiereZeileZurRessourceMap(planningUnit);
                } else {
                    calculatePlanningUnits(planningUnit, planningUnit.getKindPlanningUnits());
                }
            }
        }
    }

    private void addiereZeileZurRessourceMap(final PlanningUnit planningUnit) {
        for (PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            planningUnitElement = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().findByID(planningUnitElement.getId(), true);
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

    private Double getCosts(final PlanningUnitElement planningUnitElement) {
        final int minutes = planningUnitElement.getPlannedMinutes();
        final double hoursFromMinutes = minutes / MINS_HOUR;
        final Double externalEurosPerHour = planningUnitElement.getRessourceGroup().getExternalEurosPerHour();
        return hoursFromMinutes * externalEurosPerHour;
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
