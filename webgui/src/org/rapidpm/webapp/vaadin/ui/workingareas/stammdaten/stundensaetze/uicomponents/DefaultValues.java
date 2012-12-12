package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 13.09.12
 * Time: 12:03
 * This is part of the RapidPM - www.rapidpm.org internalcosts. please contact chef@sven-ruppert.de
 */
public enum DefaultValues {
    Double("1,0"),
    Integer("1"),
    String("neu");

    private final String defaultValue;

    private DefaultValues(final String value) {
        this.defaultValue = value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
