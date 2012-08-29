package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.deprecated;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 12.04.12
 * Time: 13:24
 */
public class Projekt extends Teil {

    private final List<Baugruppe> baugruppen = new ArrayList<>();

    public Projekt(final String name) {
        super(name);
    }

    public Baugruppe addBaugruppe(final String name) {
        final Baugruppe baugruppe = new Baugruppe(name);
        baugruppen.add(baugruppe);
        return baugruppe;
    }

    public List<Baugruppe> getBaugruppen() {
        return baugruppen;
    }
}
