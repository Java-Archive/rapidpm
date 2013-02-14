package org.rapidpm.webapp.vaadin.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 12.02.13
 * Time: 16:12
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public abstract class ConfirmDialog extends RapidWindow {

    private final MainUI ui;
    private final ResourceBundle messages;
    private SaveCancelButtonLeiste buttonLeiste;
    private Label textLabel;

    public ConfirmDialog(final String dialogtext, final Screen screen){
        this.ui = screen.getUi();
        this.messages = screen.getMessagesBundle();
        setCaption(messages.getString("confirm"));
        textLabel = new Label(dialogtext);
        setModal(true);
        buttonLeiste = new SaveCancelButtonLeiste() {
            @Override
            public void doInternationalization() {
                saveButton.setCaption(messages.getString("ok"));
                cancelButton.setCaption(messages.getString("cancel"));
            }

            @Override
            public void setSaveButtonListener() {
                saveButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        close();
                        doThisOnOK();
                    }
                });
            }

            @Override
            public void setCancelButtonListener() {
                cancelButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        close();
                        doThisOnCancel();
                    }
                });
            }
        };
        addComponent(textLabel);
        addComponent(buttonLeiste);
    }

    public void setModal(boolean b){
        super.setModal(b);
    }

    public abstract void doThisOnOK();
    public abstract void doThisOnCancel();


}
