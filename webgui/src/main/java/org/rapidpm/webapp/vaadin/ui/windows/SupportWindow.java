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
import org.rapidpm.webapp.vaadin.ui.RapidWindow;

public class SupportWindow extends RapidWindow {

  private static final String SUPPORT_HTML_SNIPPET = "Support </br> stellen Sie Ihre Anfrage.... hier kommt dann och ein Ticket Fenster..";

  public SupportWindow() {
    setCaption("Support");
    addComponent(new Label(SUPPORT_HTML_SNIPPET, ContentMode.HTML));
  }


}
