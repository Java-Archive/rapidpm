package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.components.ButtonComponent;

public class DeleteRowClickListener implements ClickListener {
    private ButtonComponent button;
    private StundensaetzeScreen screen;
    private MainRoot root;

    public DeleteRowClickListener(MainRoot root, StundensaetzeScreen screen, ButtonComponent button) {
        this.screen = screen;
        this.button = button;
        this.root = root;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        final DeleteRowLogic logic = new DeleteRowLogic(root, screen, button);
        logic.execute();
    }

}
