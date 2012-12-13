package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator;

import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 03.12.12
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractIssueBaseControllingUnitContainerCalculator<C, E>
        extends  AbstractPlannedProjectControllingUnitContainerCalculator<C, E> {

    protected final ControllingUnitContainer<C> totalSubIssueControllingContainer = new ControllingUnitContainer<>();
    protected final ControllingUnitContainer<C> ownControllingUnitContainer = new ControllingUnitContainer<>();

    public AbstractIssueBaseControllingUnitContainerCalculator(E baseEntitiy) {
        super(baseEntitiy);
    }

    public void calculate(){
        calculateSubIssues();
        calculateOwn();
        calculateTotal();
    }

    protected abstract void calculateOwn();
    protected abstract void calculateSubIssues();
    protected abstract void setControllingUnitContainer(ControllingUnitContainer container, C actual, C planned);

    public ControllingUnitContainer<C> getOwnControllingUnitContainer() {
        return ownControllingUnitContainer;
    }

    public ControllingUnitContainer<C> getTotalSubIssueControllingContainer() {
        return totalSubIssueControllingContainer;
    }


}
