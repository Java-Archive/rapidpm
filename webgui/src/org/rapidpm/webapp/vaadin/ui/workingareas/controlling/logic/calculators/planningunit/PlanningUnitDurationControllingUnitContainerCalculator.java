package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculators.planningunit;

import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculators.IssueBaseControllingUnitContainerCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculators.PlanningUnitControllingUnitContainerCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculators.issues.IssueDurationControllingUnitContainerCalculator;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 03.12.12
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class PlanningUnitDurationControllingUnitContainerCalculator extends
        PlanningUnitControllingUnitContainerCalculator<Integer, PlanningUnit> {

    public PlanningUnitDurationControllingUnitContainerCalculator(PlanningUnit baseEntity) {
        super(baseEntity);
    }

    @Override
    protected void calculateSubPlanningUnits() {
        int plannedTotalDuration = 0;
        int actualTotalDuration  = 0;

        for(PlanningUnit subPlanningUnit : baseEntitiy.getKindPlanningUnits()){
            ControllingUnitContainer<Integer> subIssueControllingUnitContainer
                    = new PlanningUnitDurationControllingUnitContainerCalculator(subPlanningUnit)
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

        for(IssueBase subIssue : baseEntitiy.getIssueBaseList()){
            ControllingUnitContainer<Integer> subIssueControllingUnitContainer
                    = new IssueDurationControllingUnitContainerCalculator(subIssue)
                    .getTotalControllingUnitContainer();

            plannedTotalDuration += subIssueControllingUnitContainer.getPlannedAbsolutte();
            actualTotalDuration += subIssueControllingUnitContainer.getActualAbsolute();
        }

        setControllingUnitContainer(totalSubIssueControllingContainer, plannedTotalDuration, actualTotalDuration);
    }

    @Override
    protected void setControllingUnitContainer(ControllingUnitContainer container, Integer actual, Integer planned) {
        totalControllingUnitContainer.setPlannedAbsolutte(planned);
        totalControllingUnitContainer.setActualAbsolute(actual);
        totalControllingUnitContainer.setRemainingAbsolute(planned - actual);
        double usedRelative = (double) planned / (double) actual;
        double remainingRelative = 1 - usedRelative;
        totalControllingUnitContainer.setUsedRelative((usedRelative));
        totalControllingUnitContainer.setRemainingRelative(remainingRelative);
    }
}
