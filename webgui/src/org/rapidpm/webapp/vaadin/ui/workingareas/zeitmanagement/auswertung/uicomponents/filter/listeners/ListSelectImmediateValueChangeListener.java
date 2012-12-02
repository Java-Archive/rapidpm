package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.uicomponents.filter.listeners;

import com.vaadin.data.Property;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.ZeitauswertungScreen;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 09.11.12
 * Time: 12:16
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ListSelectImmediateValueChangeListener implements Property.ValueChangeListener {
    private final ListSelect angezeigteSpalten;
    private final Table tabelle;

    public ListSelectImmediateValueChangeListener(final ListSelect angezeigteSpalten, final Table tabelle){
        this.angezeigteSpalten = angezeigteSpalten;
        this.tabelle = tabelle;
    }
    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        final Set<String> chosenColumns = (Set<String>)angezeigteSpalten.getValue();
        for(final String spalte : ZeitauswertungScreen.VISIBLE_COLUMNS){
            tabelle.setColumnCollapsed(spalte, true);
        }
        for (final String chosenColumn : chosenColumns) {
            tabelle.setColumnCollapsed(chosenColumn, false);
        }
    }
}
