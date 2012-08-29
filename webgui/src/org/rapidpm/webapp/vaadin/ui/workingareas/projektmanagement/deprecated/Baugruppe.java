package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.deprecated;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 12.04.12
 * Time: 13:26
 */
public class Baugruppe extends Teil {

    private final List<Einzelteil> einzelteile = new ArrayList<>();

    public Baugruppe(final String name) {
        super(name);
    }

    public Einzelteil addEinzelteil(final String name) {
        final Einzelteil einzelteil = new Einzelteil(name);
        einzelteile.add(einzelteil);
        return einzelteil;
    }

    public List<Einzelteil> getEinzelteile() {
        return einzelteile;
    }
}
