package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculators;

import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 03.12.12
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class IssueBaseControllingUnitContainerCalculator<C, E> {
    protected E baseEntitiy;

    protected final ControllingUnitContainer<C> totalSubIssueControllingContainer = new ControllingUnitContainer<>();
    protected final ControllingUnitContainer<C> ownControllingUnitContainer = new ControllingUnitContainer<>();
    protected final ControllingUnitContainer<C> totalControllingUnitContainer = new ControllingUnitContainer<>();

    public IssueBaseControllingUnitContainerCalculator(E baseEntitiy) {
        this.baseEntitiy = baseEntitiy;
    }

    protected IssueBaseControllingUnitContainerCalculator() {
    }

    public void calculate(){
        calculateSubIssues();
        calculateOwn();
        calculateTotal();
    }

    protected abstract void calculateTotal();
    protected abstract void calculateOwn();
    protected abstract void calculateSubIssues();
    protected abstract void setControllingUnitContainer(ControllingUnitContainer container, C actual, C planned);

    public ControllingUnitContainer<C> getOwnControllingUnitContainer() {
        return ownControllingUnitContainer;
    }

    public ControllingUnitContainer<C> getTotalControllingUnitContainer() {
            return totalControllingUnitContainer;
    }

    public ControllingUnitContainer<C> getTotalSubIssueControllingContainer() {
        return totalSubIssueControllingContainer;
    }


}
