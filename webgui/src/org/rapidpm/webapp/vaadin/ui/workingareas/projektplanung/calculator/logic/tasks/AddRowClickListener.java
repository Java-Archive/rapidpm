package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tasks;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.CalculatorScreen;

public class AddRowClickListener implements ClickListener {
    private MainRoot root;
    private CalculatorScreen screen;

    public AddRowClickListener(MainRoot root, CalculatorScreen screen) {
        this.root = root;
        this.screen = screen;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        AddRowLogic logic = new AddRowLogic(root, screen);
        logic.execute();
    }

}
