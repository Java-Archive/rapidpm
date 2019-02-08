package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.renderer.TextRenderer;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.EditModes;

import java.util.ResourceBundle;


public class EditOptionButtonGroup extends RadioButtonGroup<EditModes> {


    public EditOptionButtonGroup(final ResourceBundle bundle) {
        setLabel(bundle.getString("stdsatz_editMode"));
        setItems(EditModes.ROWEDIT, EditModes.NOEDIT);
        setRenderer(new TextRenderer<>(editModes -> editModes.getReadableString(getValue())));
    }

}
