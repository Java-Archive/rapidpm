package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tableedit;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import org.rapidpm.webapp.vaadin.Constants;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.CalculatorFieldsComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.CalculatorTableComputer;

public class EditGroupValueChangeListener implements ValueChangeListener {
    private OptionGroup editGroup;
    private Button saveButton;
    private Table tabelle;
    private TextField betriebsstdField;
    private TextField betriebsfraField;
    private Layout formLayout;
    private Layout upperFormLayout;
    private Layout lowerFormLayout;

    public EditGroupValueChangeListener(Layout formLayout, Layout upperFormLayout, Layout lowerFormLayout, OptionGroup group,
                                        TextField betriebsstdField, TextField betriebsfraField,
                                        Button saveButton, Table tabelle) {
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
    public void valueChange(ValueChangeEvent event) {
        if (event.getProperty().getValue() != null) {
            if (event.getProperty().getValue().equals(Constants.TABLEEDIT)) {
                formLayout.setVisible(true);
                upperFormLayout.setVisible(false);
                lowerFormLayout.setVisible(true);
                tabelle.setValue(null);
                EditModeGetter.setMode(EditModes.TABLEEDIT);
                saveButton.setVisible(true);
                tabelle.setReadOnly(true);
                tabelle.setSelectable(false);
                tabelle.setEditable(true);
                for (final Object listener : saveButton
                        .getListeners(Event.class)) {
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
                        editGroup.setValue(Constants.ROWEDIT);
                        tabelle.setEditable(false);
                        tabelle.setSelectable(true);
                        final CalculatorTableComputer computer = new CalculatorTableComputer(
                                tabelle);
                        computer.computeColumns();

                        final CalculatorFieldsComputer fieldsComputer = new CalculatorFieldsComputer(
                                tabelle);
                        fieldsComputer.compute();
                        betriebsfraField.setValue(fieldsComputer
                                .getBetriebsFraAsString());
                        betriebsstdField.setValue(fieldsComputer
                                .getBetriebsStundeAsString());
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
