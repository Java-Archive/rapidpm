package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeFieldsCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeTableCalculator;

import java.util.ResourceBundle;

public class EditGroupValueChangeListener implements ValueChangeListener {
    private OptionGroup editGroup;
    private Button saveButton;
    private Table tabelle;
    private TextField betriebsstdField;
    private TextField betriebsfraField;
    private Layout formLayout;
    private Layout upperFormLayout;
    private Layout lowerFormLayout;
    private ResourceBundle messages;

    public EditGroupValueChangeListener(final ResourceBundle bundle, final Layout formLayout, final Layout upperFormLayout,
                                        final Layout lowerFormLayout, final OptionGroup group,
                                        final TextField betriebsstdField, final TextField betriebsfraField,
                                        final Button saveButton, final Table tabelle) {
        this.messages = bundle;
        this.betriebsfraField = betriebsfraField;
        this.betriebsstdField = betriebsstdField;
        this.editGroup = group;
        this.saveButton = saveButton;
        this.tabelle = tabelle;
        this.formLayout = formLayout;
        this.upperFormLayout = upperFormLayout;
        this.lowerFormLayout = lowerFormLayout;
    }

    @Override
    public void valueChange(final ValueChangeEvent event) {
        final String TABLEMODE = messages.getString("stdsatz_tablemode");
        final String ROWMODE = messages.getString("stdsatz_rowmode");
        final Property property = event.getProperty();
        final Object value = property.getValue();
        if (value != null) {
            if (value.equals(TABLEMODE)) {
                formLayout.setVisible(true);
                upperFormLayout.setVisible(false);
                lowerFormLayout.setVisible(true);
                tabelle.setValue(null);
                EditModeGetter.setMode(EditModes.TABLEEDIT);
                saveButton.setVisible(true);
                tabelle.setReadOnly(true);
                tabelle.setSelectable(false);
                tabelle.setEditable(true);
                for (final Object listener : saveButton.getListeners(Event.class)) {
                    if (listener instanceof ClickListener) {
                        saveButton.removeListener((ClickListener) listener);
                    }

                }
                saveButton.addListener(new ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        tabelle.commit();
                        saveButton.setVisible(false);
                        EditModeGetter.setMode(EditModes.ROWEDIT);
                        editGroup.setValue(ROWMODE);
                        tabelle.setEditable(false);
                        tabelle.setSelectable(true);
                        final StundensaetzeTableCalculator calculator = new StundensaetzeTableCalculator(tabelle);
                        calculator.computeColumns();

                        final StundensaetzeFieldsCalculator fieldsCalculator = new StundensaetzeFieldsCalculator(tabelle);
                        fieldsCalculator.compute();
                        betriebsfraField.setValue(fieldsCalculator.getBetriebsFraAsString());
                        betriebsstdField.setValue(fieldsCalculator.getBetriebsStundeAsString());
                    }
                });

            } else {
                EditModeGetter.setMode(EditModes.ROWEDIT);
                tabelle.setReadOnly(false);
                tabelle.setSelectable(true);
                tabelle.setEditable(false);
                upperFormLayout.setVisible(true);
                lowerFormLayout.setVisible(true);
                formLayout.setVisible(false);
            }
        }

    }

}
