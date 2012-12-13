package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator;

import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 03.12.12
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPlanningUnitControllingUnitContainerCalculator<C,E>
    extends AbstractIssueBaseControllingUnitContainerCalculator<C,E> {

    protected ControllingUnitContainer<C> totalSubPlanningUnitControllingContainer;

    public AbstractPlanningUnitControllingUnitContainerCalculator(E baseEntity) {
        super(baseEntity);
        calculateSubIssues();
        calculateSubPlanningUnits();
        calculateOwn();
        calculateTotal();
    }

    protected abstract void calculateSubPlanningUnits();
}
