package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.RowEditFieldFactory;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.util.ArrayList;
import java.util.List;

public class StundensaetzeItemClickListener implements ComponentEventListener {

    private final Logger logger = Logger.getLogger(StundensaetzeItemClickListener.class);

    private List<ItemClickDependentComponent> components;

    private boolean state = false;
    private HorizontalLayout saveButtonLayout;
    private Button deleteButton;
    private Grid tabelle;

    public StundensaetzeItemClickListener(final List<ItemClickDependentComponent> components,
                                          final Button deleteButton, final HorizontalLayout saveButtonLayout,
                                          final Grid grid) {
        this.components = components;
        this.deleteButton = deleteButton;
        this.saveButtonLayout = saveButtonLayout;
        this.tabelle = grid;
        informComponents(state);
    }

    @Override
    public void onComponentEvent(ComponentEvent itemClickEvent) {
        tabelle.setEnabled(true);
        ItemClickEvent event = (ItemClickEvent) itemClickEvent;
        final RessourceGroup selectedRessourceGroup = (RessourceGroup) event.getItem();
        saveButtonLayout.setVisible(true);
//        final RowEditFieldFactory fieldFactory = new RowEditFieldFactory(event.getItem());
        deleteButton.setEnabled(true);
//        tabelle.setR(fieldFactory);
        if (selectedRessourceGroup == null)
            state = false;
        else {
            final Object itemId = selectedRessourceGroup;
            informComponents(itemId);
            state = true;
        }
        informComponents(state);
    }

    private void informComponents(final boolean state) {
        for (final ItemClickDependentComponent component : components) {
            component.setEnabled(state);
        }
    }

    private void informComponents(final Object itemId) {
        for (final ItemClickDependentComponent component : components) {
            component.setItemId(itemId);
        }
    }

}
