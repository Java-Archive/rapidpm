package org.rapidpm.webapp.vaadin.ui.windows;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 22:38
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.flow.component.html.Label;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;

public class SupportWindow extends RapidWindow {
    private static final Logger logger = Logger.getLogger(SupportWindow.class);

    private static final String SUPPORT_HTML_SNIPPET = "Support </br> stellen Sie Ihre Anfrage.... hier kommt dann och ein Ticket Fenster..";

    public SupportWindow() {
        add(new Label("Support"));
        add(new Label(SUPPORT_HTML_SNIPPET));
    }


}
