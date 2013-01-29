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

    private final ResourceBundle messages;
    protected Button saveButton = new Button();
    protected Button cancelButton = new Button();

    public SaveCancelButtonLeiste(final ResourceBundle messages){
        this.messages = messages;
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancelButton.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        addComponents(saveButton, cancelButton);
        setSizeUndefined();
        setSaveButtonListener();
        setCancelButtonListener();
        doInternationalization();
    }

    @Override
    public void doInternationalization() {
        saveButton.setCaption(messages.getString("save"));
        cancelButton.setCaption(messages.getString("cancel"));
    }

    public abstract void setSaveButtonListener();
    public abstract void setCancelButtonListener();
}
