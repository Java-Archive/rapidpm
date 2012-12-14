package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project;

import org.rapidpm.persistence.prj.projectmanagement.controlling.BaseControllingunit;
import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 13.12.12
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
public class BaseControllingUnitBuilder {

    private ControllingUnitContainer<Integer> duration;
    private ControllingUnitContainer<Double>  internalCosts;
    private ControllingUnitContainer<Double> externalCosts;
    private ControllingUnitContainer<String> status;


    public BaseControllingUnitBuilder setDuration(ControllingUnitContainer<Integer> duration) {
        this.duration = duration;
        return this;
    }

    public BaseControllingUnitBuilder setInternalCosts(ControllingUnitContainer<Double> internalCosts) {
        this.internalCosts = internalCosts;
        return this;
    }

    public BaseControllingUnitBuilder setExternalCosts(ControllingUnitContainer<Double> externalCosts) {
        this.externalCosts = externalCosts;
        return this;
    }

    public BaseControllingUnitBuilder setStatus(ControllingUnitContainer<String> status) {
        this.status = status;
        return this;
    }

    public BaseControllingunit getBaseControllingUnit(){
        BaseControllingunit demoBaseControllingunit = new BaseControllingunit();
        demoBaseControllingunit.setDuration(duration);
        demoBaseControllingunit.setExternalCosts(externalCosts);
        demoBaseControllingunit.setInternalCosts(internalCosts);
        demoBaseControllingunit.setStatus(status);

        return demoBaseControllingunit;
    }
    
}
