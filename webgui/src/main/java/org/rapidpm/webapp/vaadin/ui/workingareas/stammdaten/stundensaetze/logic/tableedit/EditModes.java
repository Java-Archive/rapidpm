package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit;

import com.vaadin.flow.server.VaadinSession;

import java.util.ResourceBundle;

public enum EditModes {

    NOEDIT("stdsatz_nomode"),
    ROWEDIT("stdsatz_rowmode"),
    TABLEEDIT("stdsatz_tablemode");

    private final String resourceBundleLabel;

    private EditModes(final String s){
        resourceBundleLabel = s;
    }

    public String getReadableString(EditModes editMode) {
        final ResourceBundle messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        return messages.getString(resourceBundleLabel);
    }
}
