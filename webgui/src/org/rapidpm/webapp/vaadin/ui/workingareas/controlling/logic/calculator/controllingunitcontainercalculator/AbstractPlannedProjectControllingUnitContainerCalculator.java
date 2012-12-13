package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator;

import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 13.12.12
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPlannedProjectControllingUnitContainerCalculator<C, E> {
    protected E baseEntitiy;
    protected final ControllingUnitContainer<C> totalControllingUnitContainer = new ControllingUnitContainer<>();

    public AbstractPlannedProjectControllingUnitContainerCalculator(E baseEntitiy) {
        this.baseEntitiy = baseEntitiy;
    }


    public void calculate(){
        calculateTotal();
    }

    protected abstract void calculateTotal();
    protected abstract void setControllingUnitContainer(ControllingUnitContainer container, C actual, C planned);

    public ControllingUnitContainer<C> getTotalControllingUnitContainer() {
        return totalControllingUnitContainer;
    }
}
