package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;

public class AddRowClickListener implements ComponentEventListener {

    @Override
    public void onComponentEvent(ComponentEvent componentEvent) {
        final AddRowLogic logic = new AddRowLogic();
        logic.execute();
    }
}
