package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.tabsheet.sheets;

import org.rapidpm.webapp.vaadin.ui.components.highchatrsjs.JsHighChart;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.tabsheet.AbstractControllingTabLayout;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 16.11.12
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class ControllingChartTab extends AbstractControllingTabLayout {

    private JsHighChart controllingChart;

    public ControllingChartTab(){
        super("Reporting");
        controllingChart = new  JsHighChart();
        controllingChart.setId("myJSComponent");

        addComponent(controllingChart);
        setSizeFull();
    }

}
