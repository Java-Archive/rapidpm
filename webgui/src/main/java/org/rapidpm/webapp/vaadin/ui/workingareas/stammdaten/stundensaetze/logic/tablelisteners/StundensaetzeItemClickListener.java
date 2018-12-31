package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.RowEditFieldFactory;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.util.ArrayList;
import java.util.List;

public class StundensaetzeItemClickListener {

    private final Logger logger = Logger.getLogger(StundensaetzeItemClickListener.class);

    private List<ItemClickDependentComponent> components = new ArrayList<ItemClickDependentComponent>();

    private boolean state = false;
    private HorizontalLayout saveButtonLayout;
    private Button deleteButton;
    private Grid tabelle;

    public StundensaetzeItemClickListener(final List<ItemClickDependentComponent> components,
                                          final Button deleteButton, final HorizontalLayout saveButtonLayout,
                                          final Grid tabelle) {
        this.components = components;
        this.deleteButton = deleteButton;
        this.saveButtonLayout = saveButtonLayout;
        this.tabelle = tabelle;
        informComponents(state);
    }

//    @Override
//    public void itemClick(final ItemClickEvent event) {
//        tabelle.setEditable(true);
//        final RessourceGroup selectedRessourceGroup = (RessourceGroup) event.getItemId();
//            saveButtonLayout.setVisible(true);
//            final RowEditFieldFactory fieldFactory = new RowEditFieldFactory(event.getItem());
//            deleteButton.setEnabled(true);
//            tabelle.setTableFieldFactory(fieldFactory);
//            if (selectedRessourceGroup == null)
//                state = false;
//            else {
//                final Object itemId = selectedRessourceGroup;
//                informComponents(itemId);
//                state = true;
//            }
//            informComponents(state);
//
//    }

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
