package org.rapidpm.webapp.vaadin.ui.windows;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 22:38
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;

public class ImpressumWindow extends RapidWindow {
    private static final Logger logger = Logger.getLogger(ImpressumWindow.class);

    private static final String IMPRESSUM_HTML_SNIPPET = "Impressum </br> RapidPM HQ Tannenstr. 9 82049 Pullach";

    public ImpressumWindow() {
        setCaption("Impressum");
        addComponent(new Label(IMPRESSUM_HTML_SNIPPET, ContentMode.HTML));
    }


}
