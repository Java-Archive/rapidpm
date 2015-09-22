package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit;

public class EditModeGetter {
  private static EditModes mode;

  public static EditModes getMode() {
    return mode;
  }

  public static void setMode(final EditModes mode) {
    EditModeGetter.mode = mode;
  }


}
