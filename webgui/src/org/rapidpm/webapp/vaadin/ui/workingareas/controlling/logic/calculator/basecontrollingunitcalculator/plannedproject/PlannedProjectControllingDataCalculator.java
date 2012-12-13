package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.basecontrollingunitcalculator.plannedproject;

import org.rapidpm.persistence.prj.projectmanagement.controlling.BaseControllingunit;
import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.basecontrollingunitcalculator.planningunit.PlanningUnitControllingDataCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.plannedproject.PlannedProjectDurationCalculator;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 12.12.12
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */
public class PlannedProjectControllingDataCalculator {

    public PlannedProjectControllingDataCalculator(PlannedProject plannedProject){
        plannedProject.setTotalProjectControllingunit(new BaseControllingunit());

        if(!plannedProject.getPlanningUnits().isEmpty())
            for(final PlanningUnit subPlanningUnit : plannedProject.getPlanningUnits())
                new PlanningUnitControllingDataCalculator(subPlanningUnit);

        PlannedProjectDurationCalculator durationCalculator
                = new PlannedProjectDurationCalculator(plannedProject);

        ControllingUnitContainer<Integer> totalDuration
                = durationCalculator.getTotalControllingUnitContainer();
        plannedProject.getTotalProjectControllingunit().setDuration(totalDuration);
    }
}
