package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculators;

import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 03.12.12
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public abstract class PlanningUnitControllingUnitContainerCalculator<C,E>
    extends IssueBaseControllingUnitContainerCalculator<C,E>{

    protected ControllingUnitContainer<C> totalSubPlanningUnitControllingContainer;

    public PlanningUnitControllingUnitContainerCalculator(E baseEntity) {
        super(baseEntity);
        calculateSubIssues();
        calculateSubPlanningUnits();
        calculateOwn();
        calculateTotal();
    }

    protected abstract void calculateSubPlanningUnits();
}
