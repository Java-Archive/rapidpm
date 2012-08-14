package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tasks;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.CalculatorScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.uicomponents.ButtonComponent;

public class DelRowClickListener implements ClickListener {
    private ButtonComponent button;
    private CalculatorScreen screen;

    public DelRowClickListener(CalculatorScreen screen, ButtonComponent button) {
        this.screen = screen;
        this.button = button;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        final DelRowLogic logic = new DelRowLogic(screen, button);
        logic.execute();
    }

}
