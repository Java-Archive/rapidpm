package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;

import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
  private       ResourceBundle              messages;
  //    private CostsCalcutorBean bean;
  private       PlannedProject              projekt;

  private Double totalCostsExakt = 0.0;

  public CostsCalculator(final ResourceBundle bundle) {
    final DaoFactory     daoFactory         = DaoFactorySingelton.getInstance();
    final PlannedProject projectFromSession = VaadinSession.getCurrent()
                                                           .getAttribute(PlannedProject.class);
    projekt = daoFactory.getPlannedProjectDAO()
                        .findByID(projectFromSession.getId());
    daoFactory.getEntityManager()
              .refresh(projekt);
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
    if (planningUnits == null || planningUnits.isEmpty()) {
      addiereZeileZurRessourceMap(parentPlanningUnit);
    } else {
      for (final PlanningUnit planningUnit : planningUnits) {
        if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits()
                                                                       .isEmpty()) {
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
      if (!ressourceGroup.getName()
                         .equals(messages.getString("aufgabe"))) {
        final RessourceGroup ressourceGroup1 = ressourceGroup;
        Double               costs           = getCosts(planningUnitElement);
        if (ressourceGroupsCostsMap.containsKey(ressourceGroup1)) {
          costs += ressourceGroupsCostsMap.get(ressourceGroup1);
        }
        ressourceGroupsCostsMap.put(ressourceGroup1, costs);
      }
    }
  }

  private Double getCosts(final PlanningUnitElement planningUnitElement) {
    final int    minutes              = planningUnitElement.getPlannedMinutes();
    final double hoursFromMinutes     = (double) minutes / MINS_HOUR;
    final Double externalEurosPerHour = planningUnitElement.getRessourceGroup()
                                                           .getExternalEurosPerHour();
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
