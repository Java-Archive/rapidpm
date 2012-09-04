package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tableedit;

public class EditModeGetter {
    private static EditModes mode;

    public static EditModes getMode() {
        return mode;
    }

    public static void setMode(final EditModes mode) {
        EditModeGetter.mode = mode;
    }


}
