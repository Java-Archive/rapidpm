package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.controlling;

import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.components.highchatrsjs.JsHighChart;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 05.11.12
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */
public class ControllingScreen extends Screen {
    public ControllingScreen(final MainUI ui) {
        super(ui);
        JsHighChart chart = new JsHighChart();
        chart.setId("myJSComponent");
        addComponent(chart);
    }

    @Override
    public void setComponents() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doInternationalization() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
