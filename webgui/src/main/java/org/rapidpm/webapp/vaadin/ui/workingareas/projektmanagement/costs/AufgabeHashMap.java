package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs;

import java.util.HashMap;

public class AufgabeHashMap extends HashMap<String, String> {

    @Override
    public boolean equals(Object o) {
        HashMap<String, String> otherMap = (HashMap<String, String>) o;
        return otherMap.get("Aufgabe").equals(get("Aufgabe"));
    }

    @Override
    public int hashCode() {
        return get("Aufgabe").hashCode();
    }
}
