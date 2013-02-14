package org.rapidpm.webapp.vaadin.ui;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 15.01.13
 * Time: 13:27
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public abstract class SaveCancelButtonLeiste extends HorizontalLayout implements Internationalizationable {

    protected Button saveButton = new Button();
    protected Button cancelButton = new Button();

    public SaveCancelButtonLeiste(){
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancelButton.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        addComponents(saveButton, cancelButton);
        setSizeUndefined();
        setSaveButtonListener();
        setCancelButtonListener();
        doInternationalization();
    }

    @Override
    public abstract void doInternationalization();
    public abstract void setSaveButtonListener();
    public abstract void setCancelButtonListener();
}
