package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.ui.OptionGroup;

import java.util.ResourceBundle;


public class EditOptionButtonGroup extends OptionGroup {
    public EditOptionButtonGroup(ResourceBundle bundle) {
        addItem(bundle.getString("stdsatz_tablemode"));
        addItem(bundle.getString("stdsatz_rowmode"));
    }

}
