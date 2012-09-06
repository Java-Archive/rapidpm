package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ButtonComponent;

public class DelRowClickListener implements ClickListener {
    private ButtonComponent button;
    private StundensaetzeScreen screen;
    private MainRoot root;

    public DelRowClickListener(final MainRoot root, final StundensaetzeScreen screen, final ButtonComponent button) {
        this.screen = screen;
        this.button = button;
        this.root = root;
    }

    @Override
    public void buttonClick(final ClickEvent event) {
        final DelRowLogic logic = new DelRowLogic(root, screen, button);
        logic.execute();
    }

}
