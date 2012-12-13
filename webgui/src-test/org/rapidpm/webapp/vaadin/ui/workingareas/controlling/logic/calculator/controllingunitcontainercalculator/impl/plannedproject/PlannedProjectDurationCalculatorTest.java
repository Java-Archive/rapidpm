package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.plannedproject;

import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.AbstractPlannedProjectControllingUnitContainerCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.planningunit.PlanningUnitDurationCalculatorTest;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 13.12.12
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
public class PlannedProjectDurationCalculatorTest extends
        AbstractPlannedProjectControllingUnitContainerCalculator<Integer, PlannedProject> {

    public PlannedProjectDurationCalculatorTest(PlannedProject baseEntitiy) {
        super(baseEntitiy);
    }

    @Override
    protected void calculateTotal() {
        int plannedTotalDuration = 0;
        int actualTotalDuration  = 0;

        for(final PlanningUnit subPlanningUnit : baseEntitiy.getPlanningUnits()){
            final ControllingUnitContainer<Integer> subIssueControllingUnitContainer
                    = new PlanningUnitDurationCalculatorTest(subPlanningUnit)
                    .getTotalControllingUnitContainer();

            plannedTotalDuration += subIssueControllingUnitContainer.getPlannedAbsolutte();
            actualTotalDuration += subIssueControllingUnitContainer.getActualAbsolute();
        }

        setControllingUnitContainer(totalControllingUnitContainer, plannedTotalDuration, actualTotalDuration);
    }

    @Override
    protected void setControllingUnitContainer(ControllingUnitContainer container, Integer actual, Integer planned) {
        totalControllingUnitContainer.setPlannedAbsolutte(planned);
        totalControllingUnitContainer.setActualAbsolute(actual);
        totalControllingUnitContainer.setRemainingAbsolute(planned - actual);
        final double usedRelative = (double) planned / (double) actual;
        final double remainingRelative = 1 - usedRelative;
        totalControllingUnitContainer.setUsedRelative((usedRelative));
        totalControllingUnitContainer.setRemainingRelative(remainingRelative);
    }
}
