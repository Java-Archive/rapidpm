package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.components.AddRowWindow;

public class AddRowLogic {
    private MainRoot root;
    private StundensaetzeScreen screen;

    public AddRowLogic(MainRoot root, StundensaetzeScreen screen) {
        this.root = root;
        this.screen = screen;

    }

    public void execute() {
        final AddRowWindow window = new AddRowWindow(root, screen);
        window.show();
    }
}
