package org.rapidpm.webapp.vaadin.ui;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.Window;

/**
 *
 * User: svenruppert
 * Date: 28.07.12
 * Time: 08:40
 *
 */

@JavaScript({"VAADIN/scripts/jquery/jquery-1.4.4.min.js",
        "VAADIN/scripts/highcharts/highcharts.js",
        "AADIN/scripts/highcharts/modules/exporting.js"})
public class MainWindow extends Window {

    public MainWindow(String caption) {
        super(caption);
        //TODO Aus der BaseApplication den Aufbau der UI rausziehen.
    }
}
