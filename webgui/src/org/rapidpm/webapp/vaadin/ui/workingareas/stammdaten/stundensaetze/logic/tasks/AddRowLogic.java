package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.AddRowWindow;

public class AddRowLogic {
    private MainUI ui;
    private StundensaetzeScreen screen;

    public AddRowLogic(final MainUI ui, final StundensaetzeScreen screen) {
        this.ui = ui;
        this.screen = screen;

    }

    public void execute() {
        final AddRowWindow window = new AddRowWindow(ui, screen);
        window.show();
    }
}
