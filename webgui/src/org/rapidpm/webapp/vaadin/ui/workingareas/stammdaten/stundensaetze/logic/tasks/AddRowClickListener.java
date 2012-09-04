package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;

public class AddRowClickListener implements ClickListener {
    private MainRoot root;
    private StundensaetzeScreen screen;

    public AddRowClickListener(MainRoot root, StundensaetzeScreen screen) {
        this.root = root;
        this.screen = screen;
    }

    @Override
    public void buttonClick(final ClickEvent event) {
        AddRowLogic logic = new AddRowLogic(root, screen);
        logic.execute();
    }

}
