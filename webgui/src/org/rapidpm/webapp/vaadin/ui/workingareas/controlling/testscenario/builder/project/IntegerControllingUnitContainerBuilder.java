package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project;

import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 19.12.12
 * Time: 04:22
 * To change this template use File | Settings | File Templates.
 */
public class IntegerControllingUnitContainerBuilder {

    public ControllingUnitContainer<Integer> getControllingUnitContainer(int planned, int actual){
        ControllingUnitContainer<Integer> intContainer = new ControllingUnitContainer<>();
        intContainer.setPlannedAbsolutte(planned);
        intContainer.setActualAbsolute(actual);
        int remainingAbsolute = planned - actual;
        intContainer.setRemainingAbsolute(planned - actual);
        double usedRelative = 0;
        if(planned !=  0)
            usedRelative= (double) actual / (double) planned;
        intContainer.setUsedRelative(actual);
        double remainingRelative = 1 - usedRelative;
        intContainer.setRemainingRelative(remainingRelative);
        return intContainer;
    }

}
