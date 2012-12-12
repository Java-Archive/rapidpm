package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculators.issues;

import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculators.IssueBaseControllingUnitContainerCalculator;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 03.12.12
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public class IssueDurationControllingUnitContainerCalculator extends IssueBaseControllingUnitContainerCalculator<Integer,
        IssueBase> {

    public IssueDurationControllingUnitContainerCalculator(IssueBase issueBase) {
        super(issueBase);
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
        int plannedDuration = baseEntitiy.getTimeUnitEstimated().getMinutes();
        int actualDuration  = 0;

        for(IssueTimeUnit issueTimeUnit : baseEntitiy.getTimeUnitsUsed())
            actualDuration += issueTimeUnit.getMinutes();

        setControllingUnitContainer(totalControllingUnitContainer, plannedDuration, actualDuration);
    }

    @Override
    protected void calculateSubIssues() {
        int plannedTotalDuration = 0;
        int actualTotalDuration  = 0;

        for(IssueBase subIssue : baseEntitiy.getSubIssues()){
            ControllingUnitContainer<Integer> subIssueControllingUnitContainer
                    = new IssueDurationControllingUnitContainerCalculator(subIssue).getTotalControllingUnitContainer();

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
