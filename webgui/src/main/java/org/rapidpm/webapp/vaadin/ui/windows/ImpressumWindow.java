package org.rapidpm.webapp.vaadin.ui.windows;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 22:38
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.flow.component.html.Label;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;

public class ImpressumWindow extends RapidWindow {

    private static final String IMPRESSUM_HTML_SNIPPET = "Impressum </br> RapidPM HQ Tannenstr. 9 82049 Pullach";

    public ImpressumWindow() {
        add(new Label("Impressum"));
        add(new Label(IMPRESSUM_HTML_SNIPPET));
    }


}
