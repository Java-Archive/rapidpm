package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.GridLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.AufwandProjInitScreen;

import java.util.ArrayList;

public class TableItemClickListener implements ItemClickListener {

    private AufwandProjInitScreen screen;

    public TableItemClickListener(AufwandProjInitScreen screen) {
        this.screen = screen;
    }

    @Override
    public void itemClick(ItemClickEvent event) {
        final GridLayout formUnterlayout = screen.getUpperFormLayout();
        final FieldGroup fieldGroup = new FieldGroup(event.getItem());
        final ArrayList<Integer> ersteEbeneIds = screen.getContainer().getErsteEbeneIds();
        for (final Object listener : screen.getSaveButton().getListeners(Event.class)) {
            if (listener instanceof ClickListener) {
                screen.getSaveButton().removeListener((ClickListener) listener);
            }

        }

        formUnterlayout.removeAllComponents();

        if (!ersteEbeneIds.contains(event.getItemId())) {
            for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
                formUnterlayout.addComponent(
                        fieldGroup.buildAndBind(prop));
            }
        } else {
            for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
                if (prop.equals("Aufgabe"))
                    formUnterlayout.addComponent(
                            fieldGroup.buildAndBind(prop));
            }
        }
        screen.getSaveButton().addListener(new SaveButtonClickListener(fieldGroup, screen));
        screen.getFormLayout().setVisible(true);

    }

}
