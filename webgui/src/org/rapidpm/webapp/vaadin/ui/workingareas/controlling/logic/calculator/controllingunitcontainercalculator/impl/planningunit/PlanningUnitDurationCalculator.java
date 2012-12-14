package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.planningunit;

import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.AbstractPlanningUnitControllingUnitContainerCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.issue.IssueDurationCalulator;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 03.12.12
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class PlanningUnitDurationCalculator extends
        AbstractPlanningUnitControllingUnitContainerCalculator<Integer, PlanningUnit> {

    public PlanningUnitDurationCalculator(PlanningUnit baseEntity) {
        super(baseEntity);
    }

    @Override
    protected void calculateSubPlanningUnits() {
        int plannedTotalDuration = 0;
        int actualTotalDuration  = 0;

        for(final PlanningUnit subPlanningUnit : baseEntitiy.getKindPlanningUnits()){
            final ControllingUnitContainer<Integer> subIssueControllingUnitContainer
                    = new PlanningUnitDurationCalculator(subPlanningUnit)
                    .getTotalControllingUnitContainer();

            plannedTotalDuration += subIssueControllingUnitContainer.getPlannedAbsolutte();
            actualTotalDuration += subIssueControllingUnitContainer.getActualAbsolute();
        }

        setControllingUnitContainer(totalSubIssueControllingContainer, plannedTotalDuration, actualTotalDuration);
    }

    @Override
    protected void calculateTotal() {
        int plannedTotalDuration = ownControllingUnitContainer.getPlannedAbsolutte();
        plannedTotalDuration += totalSubIssueControllingContainer.getPlannedAbsolutte();

        int actualTotalDuration  = ownControllingUnitContainer.getActualAbsolute();
        actualTotalDuration += totalSubIssueControllingContainer.getActualAbsolute();

        setControllingUnitContainer(totalControllingUnitContainer, plannedTotalDuration, actualTotalDuration);
    }

    @Override
    protected void calculateOwn() {
        int plannedTotalDuration = ownControllingUnitContainer.getPlannedAbsolutte();
        plannedTotalDuration += totalSubIssueControllingContainer.getPlannedAbsolutte();
        plannedTotalDuration += totalSubPlanningUnitControllingContainer.getPlannedAbsolutte();

        int actualTotalDuration  = ownControllingUnitContainer.getActualAbsolute();
        actualTotalDuration += totalSubIssueControllingContainer.getActualAbsolute();
        actualTotalDuration += totalSubPlanningUnitControllingContainer.getActualAbsolute();

        setControllingUnitContainer(totalControllingUnitContainer, plannedTotalDuration, actualTotalDuration);
    }

    @Override
    protected void calculateSubIssues() {
        int plannedTotalDuration = 0;
        int actualTotalDuration  = 0;

        for(final IssueBase subIssue : baseEntitiy.getIssueBaseList()){
            ControllingUnitContainer<Integer> subIssueControllingUnitContainer
                    = new IssueDurationCalulator(subIssue)
                    .getTotalControllingUnitContainer();

            plannedTotalDuration += subIssueControllingUnitContainer.getPlannedAbsolutte();
            actualTotalDuration += subIssueControllingUnitContainer.getActualAbsolute();
        }

        setControllingUnitContainer(totalSubIssueControllingContainer, plannedTotalDuration, actualTotalDuration);
    }

    @Override
    protected void setControllingUnitContainer(ControllingUnitContainer container, Integer actual, Integer planned) {
        container.setPlannedAbsolutte(planned);
        container.setActualAbsolute(actual);
        container.setRemainingAbsolute(planned - actual);
        double usedRelative = 100;
        if(actual > 0)
            usedRelative = (double) planned / (double) actual;

        final double remainingRelative = 1 - usedRelative;
        container.setUsedRelative((usedRelative));
        container.setRemainingRelative(remainingRelative);
    }
}