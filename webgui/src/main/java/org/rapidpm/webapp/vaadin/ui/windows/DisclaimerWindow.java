package org.rapidpm.webapp.vaadin.ui.windows;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 22:38
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Embedded;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;

public class DisclaimerWindow extends RapidWindow {
    private static final Logger logger = Logger.getLogger(DisclaimerWindow.class);

    public DisclaimerWindow() {
        final Embedded e = new Embedded("Disclaimer", new ThemeResource("data/static/disclaimer.html"));
        e.setType(Embedded.TYPE_BROWSER);
        e.setWidth("400px");
        e.setHeight("600px");
        super.addComponent(e);
    }
}
