package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ButtonComponent;

import java.util.ResourceBundle;

public class DelRowClickListener implements ComponentEventListener<ClickEvent<Button>> {
    private ButtonComponent button;
    private StundensaetzeScreen screen;
    private ResourceBundle messages;

    public DelRowClickListener(final StundensaetzeScreen screen, final ButtonComponent button,
                               final ResourceBundle messages) {
        this.screen = screen;
        this.button = button;
        this.messages = messages;
    }

    @Override
    public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
        final DelRowLogic logic = new DelRowLogic(screen, button, messages);
        logic.execute();
    }

}
