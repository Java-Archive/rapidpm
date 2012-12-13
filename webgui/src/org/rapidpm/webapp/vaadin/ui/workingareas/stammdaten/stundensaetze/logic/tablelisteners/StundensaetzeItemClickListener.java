package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.RowEditFieldFactory;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.util.ArrayList;
import java.util.List;

public class StundensaetzeItemClickListener implements ItemClickListener {

    private final Logger logger = Logger.getLogger(StundensaetzeItemClickListener.class);

    private List<ItemClickDependentComponent> components = new ArrayList<ItemClickDependentComponent>();

    private boolean state = false;
    private Layout saveButtonLayout;
    private Button deleteButton;
    private Table tabelle;

    public StundensaetzeItemClickListener(final List<ItemClickDependentComponent> components,
                                          final Button deleteButton, final Layout saveButtonLayout,
                                          final Table tabelle) {
        this.components = components;
        this.deleteButton = deleteButton;
        this.saveButtonLayout = saveButtonLayout;
        this.tabelle = tabelle;
        informComponents(state);
    }

    @Override
    public void itemClick(final ItemClickEvent event) {
        tabelle.setEditable(true);
        final RessourceGroup selectedRessourceGroup = (RessourceGroup) event.getItemId();
            saveButtonLayout.setVisible(true);
            final RowEditFieldFactory fieldFactory = new RowEditFieldFactory(event.getItem());
            deleteButton.setEnabled(true);
            tabelle.setTableFieldFactory(fieldFactory);
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
            component.getTheState(state);
        }
    }

    private void informComponents(final Object itemId) {
        for (final ItemClickDependentComponent component : components) {
            component.setItemId(itemId);
        }
    }

}
