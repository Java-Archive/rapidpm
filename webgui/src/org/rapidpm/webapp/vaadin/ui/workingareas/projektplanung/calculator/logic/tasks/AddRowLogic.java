package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tasks;

import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.CalculatorScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.uicomponents.AddRowWindow;

public class AddRowLogic {
    private MainRoot root;
    private CalculatorScreen screen;

    public AddRowLogic(final MainRoot root, final CalculatorScreen screen) {
        this.root = root;
        this.screen = screen;

    }

    public void execute() {
        final AddRowWindow window = new AddRowWindow(root, screen);
        window.show();
    }
}
