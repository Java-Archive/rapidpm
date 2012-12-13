package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.basecontrollingunitcalculator.planningunit;

import org.rapidpm.persistence.prj.projectmanagement.controlling.BaseControllingunit;
import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.basecontrollingunitcalculator.issue.IssueBaseControllingUnitCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.planningunit.PlanningUnitDurationCalculator;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 12.12.12
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */
public class PlanningUnitControllingDataCalculator {

    public PlanningUnitControllingDataCalculator(PlanningUnit planningUnit){
        planningUnit.setTotalControllingUnit(new BaseControllingunit());
        planningUnit.setTotalOwnIssuesCotntrollingUnit(new BaseControllingunit());
        planningUnit.setTotalOwnIssuesCotntrollingUnit(new BaseControllingunit());

        if(!planningUnit.getIssueBaseList().isEmpty())
            for(final IssueBase subIssue : planningUnit.getIssueBaseList())
                new IssueBaseControllingUnitCalculator(subIssue);

        if(!planningUnit.getKindPlanningUnits().isEmpty())
            for(final PlanningUnit subPlanningUnit : planningUnit.getKindPlanningUnits())
                new PlanningUnitControllingDataCalculator(subPlanningUnit);

        PlanningUnitDurationCalculator durationCalculator
                = new PlanningUnitDurationCalculator(planningUnit);

        ControllingUnitContainer<Integer> subPlanningUnitDuration
                = durationCalculator.getOwnControllingUnitContainer();
        planningUnit.getTotalSubPlaningUnitsControllingUnit().setDuration(subPlanningUnitDuration);

        ControllingUnitContainer<Integer> subIssueDuration
                = durationCalculator.getTotalSubIssueControllingContainer();
        planningUnit.getTotalOwnIssuesCotntrollingUnit().setDuration(subIssueDuration);

        ControllingUnitContainer<Integer> totalDuration
                = durationCalculator.getTotalControllingUnitContainer();
        planningUnit.getTotalControllingUnit().setDuration(totalDuration);
    }
}
