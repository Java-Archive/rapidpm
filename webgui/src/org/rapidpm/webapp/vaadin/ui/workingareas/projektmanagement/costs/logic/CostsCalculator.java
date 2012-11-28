package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;

import javax.persistence.EntityManager;
import java.text.DecimalFormat;
import java.util.*;

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
//    private CostsCalcutorBean bean;
    private PlannedProject projekt;

    private Double totalCostsExakt = 0.0;

    public CostsCalculator(MainUI ui, final ResourceBundle bundle) {
//        bean = EJBFactory.getEjbInstance(CostsCalcutorBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final PlannedProject projectFromSession = ui.getSession().getAttribute(PlannedProject.class);
        projekt = daoFactory.getPlannedProjectDAO().findByID(projectFromSession.getId());
        daoFactory.getEntityManager().refresh(projekt);
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
        final Set<PlanningUnit> planningUnits = projekt.getPlanningUnits();
        for (final PlanningUnit planningUnit : planningUnits) {
            calculatePlanningUnits(planningUnit, planningUnit.getKindPlanningUnits());
        }
    }


    private void calculatePlanningUnits(final PlanningUnit parentPlanningUnit, final Set<PlanningUnit> planningUnits) {
        if(planningUnits == null || planningUnits.isEmpty()){
            addiereZeileZurRessourceMap(parentPlanningUnit);
        } else {
            for (final PlanningUnit planningUnit : planningUnits) {
                if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                    addiereZeileZurRessourceMap(planningUnit);
                } else {
                    calculatePlanningUnits(planningUnit, planningUnit.getKindPlanningUnits());
                }
            }
        }
    }

    private void addiereZeileZurRessourceMap(final PlanningUnit planningUnit) {
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

    private Double getCosts(final PlanningUnitElement planningUnitElement) {
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
