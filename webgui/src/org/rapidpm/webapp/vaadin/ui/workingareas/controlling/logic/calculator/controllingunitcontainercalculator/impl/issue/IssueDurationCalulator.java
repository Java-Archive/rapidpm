package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.issue;

import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.AbstractIssueBaseControllingUnitContainerCalculator;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 03.12.12
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public class IssueDurationCalulator extends AbstractIssueBaseControllingUnitContainerCalculator<Integer,
        IssueBase> {

    public IssueDurationCalulator(IssueBase issueBase) {
        super(issueBase);
    }

    @Override
    protected void calculateTotal() {
        int plannedTotalDuration = ownControllingUnitContainer.getPlannedAbsolutte();
        plannedTotalDuration += totalSubIssueControllingContainer.getPlannedAbsolutte();

        int actualTotalDuration  = ownControllingUnitContainer.getActualAbsolute();
        actualTotalDuration += totalSubIssueControllingContainer.getActualAbsolute();
        baseEntitiy.getTotalControllingUnit().getDuration().setPlannedAbsolutte(plannedTotalDuration);
        baseEntitiy.getTotalControllingUnit().getDuration().setPlannedAbsolutte(actualTotalDuration);
    }

    @Override
    protected void calculateOwn() {
        int plannedDuration = baseEntitiy.getTimeUnitEstimated().getMinutes();
        int actualDuration  = 0;

        for(final IssueTimeUnit issueTimeUnit : baseEntitiy.getTimeUnitsUsed())
            actualDuration += issueTimeUnit.getMinutes();

        setControllingUnitContainer(totalControllingUnitContainer, plannedDuration, actualDuration);
    }

    @Override
    protected void calculateSubIssues() {
        int plannedTotalDuration = 0;
        int actualTotalDuration  = 0;

        for(final IssueBase subIssue : baseEntitiy.getSubIssues()){
            final ControllingUnitContainer<Integer> subIssueControllingUnitContainer
                    = subIssue.getTotalControllingUnit().getDuration();

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
