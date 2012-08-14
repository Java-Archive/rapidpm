package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tasks;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Root;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.CalculatorScreen;

public class AddRowClickListener implements ClickListener {
    private Root root;
    private CalculatorScreen screen;

    public AddRowClickListener(Root root, CalculatorScreen screen) {
        this.root = root;
        this.screen = screen;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        AddRowLogic logic = new AddRowLogic(root, screen);
        logic.execute();
    }

}
