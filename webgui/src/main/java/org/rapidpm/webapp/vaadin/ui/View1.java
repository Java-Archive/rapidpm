package org.rapidpm.webapp.vaadin.ui;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.Arrays;
import java.util.List;

@Route(value = "", layout = MainAppLayout.class)
@Caption("Home")
@Icon(VaadinIcon.HOME)
public class View1 extends VerticalLayout {

    public View1() {
        List<Paragraph> labels = getLabels();
        for (Paragraph label : labels) {
            add(label);
        }
    }

    List<Paragraph> getLabels() {
        Paragraph label = new Paragraph("Herzlich willkommen bei RapidPM.");
        Paragraph label2 = new Paragraph("Sie sind angemeldet als '" + VaadinSession.getCurrent().getAttribute(Benutzer.class).getLogin() + "'");
//        label.setWidth("100%");
        return Arrays.asList(label, label2);
    }

}