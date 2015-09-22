package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.webapp.vaadin.MainUI;

import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 13:47
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
@SuppressWarnings("ClassWithoutNoArgConstructor")
public class PlanningCalculator {

  private static final Logger logger = Logger.getLogger(PlanningCalculator.class);

  private PlannedProject projekt;
  private ResourceBundle messages;
  private DaoFactory daoFactory;


  public PlanningCalculator(final ResourceBundle bundle, MainUI ui) {
    this.messages = bundle;
    daoFactory = DaoFactorySingleton.getInstance();
    final PlannedProjectDAO plannedProjectDAO = daoFactory.getPlannedProjectDAO();
    final PlannedProject projectFromSession = ui.getSession().getAttribute(PlannedProject.class);
    projekt = plannedProjectDAO.findByID(projectFromSession.getId(), true);
  }

  public void calculate() {
    projekt.getTopLevelPlanningUnits().forEach(this::calculatePlanningUnit);
    projekt = daoFactory.getPlannedProjectDAO().findByID(projekt.getId(), true);
  }

  public void calculatePlanningUnit(PlanningUnit fullPlanningUnit) {
    fullPlanningUnit = daoFactory.getPlanningUnitDAO().findByID(fullPlanningUnit.getId(), true);
    if (fullPlanningUnit.getKindPlanningUnits() != null && !fullPlanningUnit.getKindPlanningUnits().isEmpty()) {
      for (final PlanningUnitElement planningUnitElement : fullPlanningUnit.getPlanningUnitElementList()) {
        planningUnitElement.setPlannedMinutes(0);
        daoFactory.getPlanningUnitElementDAO().updateByEntity(planningUnitElement, false);
      }
      fullPlanningUnit.getKindPlanningUnits().forEach(this::calculatePlanningUnit);
    }
    PlanningUnit parent = fullPlanningUnit.getParent();
    fullPlanningUnit = daoFactory.getPlanningUnitDAO().findByID(fullPlanningUnit.getId(), true);
    if (parent != null) {
      PlanningUnit fullParentPlanningUnit = daoFactory.getPlanningUnitDAO().findByID(parent.getId(), true);
      for (PlanningUnitElement planningUnitElement : fullPlanningUnit.getPlanningUnitElementList()) {
        for (PlanningUnitElement oneParentPlanningUnitElement : fullParentPlanningUnit.getPlanningUnitElementList()) {
          if (oneParentPlanningUnitElement.getRessourceGroup().getId().equals(planningUnitElement.getRessourceGroup().getId())) {
            if (oneParentPlanningUnitElement.getRessourceGroup().getName().equals("GF"))
              logger.debug("Adding " + planningUnitElement.getPlannedMinutes() + " from " + fullPlanningUnit.getPlanningUnitName() + " to " + fullParentPlanningUnit.getPlanningUnitName());
            oneParentPlanningUnitElement.setPlannedMinutes(oneParentPlanningUnitElement.getPlannedMinutes() + planningUnitElement.getPlannedMinutes());
            daoFactory.getPlanningUnitElementDAO().updateByEntity(oneParentPlanningUnitElement, false);
          }
        }
      }
    } else {
      // do nothing
    }
  }
}
