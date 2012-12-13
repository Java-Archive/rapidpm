package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.basecontrollingunitcalculator.issue;

import org.rapidpm.persistence.prj.projectmanagement.controlling.BaseControllingunit;
import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.issue.IssueDurationCalulator;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 12.12.12
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */
public class IssueBaseControllingUnitCalculator {

    public IssueBaseControllingUnitCalculator(IssueBase issueBase){
        issueBase.setTotalControllingUnit(new BaseControllingunit());
        issueBase.setTotalSubIssuesBaseControllingUnit(new BaseControllingunit());
        issueBase.setTotalControllingUnit(new BaseControllingunit());

        if(!issueBase.getSubIssues().isEmpty())
            for(final IssueBase subIssue : issueBase.getSubIssues())
                new IssueBaseControllingUnitCalculator(subIssue);

        IssueDurationCalulator issueDurationCalulator
                = new IssueDurationCalulator(issueBase);

        ControllingUnitContainer<Integer> ownDuration = issueDurationCalulator
                .getOwnControllingUnitContainer();
        issueBase.getTotalOwnCotntrollingUnit().setDuration(ownDuration);

        ControllingUnitContainer<Integer> subIssueDuration = issueDurationCalulator
                .getTotalSubIssueControllingContainer();
        issueBase.getTotalSubIssuesBaseControllingUnit().setDuration(subIssueDuration);

        ControllingUnitContainer<Integer> totalDuration = issueDurationCalulator
                .getTotalControllingUnitContainer();
        issueBase.getTotalControllingUnit().setDuration(totalDuration);
    }

}
