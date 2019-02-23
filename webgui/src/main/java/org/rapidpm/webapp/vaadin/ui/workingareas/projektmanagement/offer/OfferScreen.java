package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.offer;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.AbstractView;
import org.rapidpm.webapp.vaadin.ui.MainAppLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 12.09.12
 * Time: 11:45
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
@Route(value = "angebot", layout = MainAppLayout.class)
@Caption("Angebot")
@Icon(VaadinIcon.OFFICE)
public class OfferScreen extends VerticalLayout {

    public OfferScreen() {
        setComponents();
    }

    public void doInternationalization() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void setComponents() {
        add(new Paragraph("Bisher leer..."));
    }

}
