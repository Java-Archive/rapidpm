package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeFieldsCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeTableCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.EditModeGetter;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.EditModes;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.RowFieldGroup;

import java.util.ArrayList;
import java.util.List;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;

public class StundensaetzeItemClickListener implements ItemClickListener {

    private Logger logger = Logger.getLogger(StundensaetzeItemClickListener.class);

    private List<ItemClickDependentComponent> components = new ArrayList<ItemClickDependentComponent>();

    private boolean state = false;
    private Layout upperFormLayout;
    private Layout lowerFormLayout;
    private Layout formLayout;
    private Button saveButton;
    private Button deleteButton;

    private TextField betriebsWertField;
    private TextField betriebsStdField;

    private Table table;

    public StundensaetzeItemClickListener(final List<ItemClickDependentComponent> components,
                                          final Button deleteButton, final Layout upperFormLayout, final Layout lowerFormLayout,
                                          final Layout formLayout, final Button saveButton, final Table tabelle,
                                          final TextField betriebsWertField, final TextField betriebsStdField) {
        this.components = components;
        this.deleteButton = deleteButton;
        this.upperFormLayout = upperFormLayout;
        this.lowerFormLayout = lowerFormLayout;
        this.formLayout = formLayout;
        this.saveButton = saveButton;
        this.betriebsWertField = betriebsWertField;
        this.betriebsStdField = betriebsStdField;
        table = tabelle;
        informComponents(state);
    }

    @Override
    public void itemClick(ItemClickEvent event) {

        if (EditModeGetter.getMode() == EditModes.ROWEDIT) {
            formLayout.setVisible(true);
            lowerFormLayout.setVisible(true);
            upperFormLayout.setVisible(true);

            upperFormLayout.removeAllComponents();
            for (final Object listener : saveButton.getListeners(Event.class)) {
                if (listener instanceof ClickListener) {
                    saveButton.removeListener((ClickListener) listener);
                }
            }
            deleteButton.setEnabled(true);

            final Item item = event.getItem();
            final RowFieldGroup fieldGroup = new RowFieldGroup(item);
            final List<Component> components = fieldGroup.getComponents();
            for (final Component component : components) {
                upperFormLayout.addComponent(component);
            }

            saveButton.addListener(new ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {
                    try {
                        fieldGroup.commit();
                        final StundensaetzeTableCalculator calculator = new StundensaetzeTableCalculator(table);
                        calculator.computeColumns();

                        final StundensaetzeFieldsCalculator fieldsCalculator = new StundensaetzeFieldsCalculator(table);
                        fieldsCalculator.compute();
                        betriebsWertField.setValue(fieldsCalculator.getBetriebsFraAsString());
                        betriebsStdField.setValue(fieldsCalculator.getBetriebsStundeAsString());
                        upperFormLayout.setVisible(false);
                        saveButton.setVisible(false);
                    }catch (CommitException e){
                        logger.info(COMMIT_EXCEPTION_MESSAGE);
                    }catch(Exception e){
                        logger.warn("Exception", e);
                    }
                }
            });
            saveButton.setVisible(true);

            if (event.getItemId() == null)
                state = false;
            else {
                final Object itemId = event.getItemId();
                informComponents(itemId);
                state = true;
            }
            informComponents(state);
        }
    }

    private void informComponents(boolean state) {
        for (final ItemClickDependentComponent component : components) {
            component.getState(state);
        }
    }

    private void informComponents(Object itemId) {
        for (final ItemClickDependentComponent component : components) {
            component.setItemId(itemId);
        }
    }

}
