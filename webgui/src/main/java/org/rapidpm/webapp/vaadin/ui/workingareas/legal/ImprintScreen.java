package org.rapidpm.webapp.vaadin.ui.workingareas.legal;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.rapidpm.webapp.vaadin.ui.MainAppLayout;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 12.09.12
 * Time: 11:45
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
@Route(value = "imprint", layout = MainAppLayout.class)
@Caption("Impressum")
@Icon(VaadinIcon.COG)
public class ImprintScreen extends VerticalLayout {

    public ImprintScreen() {
        setComponents();
    }

    public void doInternationalization() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void setComponents() {
        add(new Paragraph("Impressum & Datenschutzerklärung"));
    }

}
