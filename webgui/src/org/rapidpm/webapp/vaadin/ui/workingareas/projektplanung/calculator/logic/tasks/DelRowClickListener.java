package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tasks;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.CalculatorScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.uicomponents.ButtonComponent;

public class DelRowClickListener implements ClickListener {
    private ButtonComponent button;
    private CalculatorScreen screen;
    private MainRoot root;

    public DelRowClickListener(MainRoot root, CalculatorScreen screen, ButtonComponent button) {
        this.screen = screen;
        this.button = button;
        this.root = root;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        final DelRowLogic logic = new DelRowLogic(root, screen, button);
        logic.execute();
    }

}
