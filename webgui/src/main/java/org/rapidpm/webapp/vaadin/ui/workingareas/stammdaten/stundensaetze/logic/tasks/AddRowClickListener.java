package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import com.vaadin.flow.component.ClickEvent;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;

public class AddRowClickListener  {
    private MainUI ui;
    private StundensaetzeScreen screen;

    public AddRowClickListener(final MainUI ui, final StundensaetzeScreen screen) {
        this.ui = ui;
        this.screen = screen;
    }

//    @Override
    public void buttonClick(final ClickEvent event) {
        final AddRowLogic logic = new AddRowLogic(ui, screen);
        logic.execute();
    }

}
