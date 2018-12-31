package org.rapidpm.webapp.vaadin.ui;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.server.VaadinSession;
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

    private final ResourceBundle messages;
    private SaveCancelButtonLeiste buttonLeiste;
    private Label textLabel;

    public ConfirmDialog(final String dialogtext){
        this.messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        add(new Label(messages.getString("confirm")));
        textLabel = new Label(dialogtext);
        setModal(true);
//        buttonLeiste = new SaveCancelButtonLeiste() {
//            @Override
//            public void doInternationalization() {
//                saveButton.setText(messages.getString("ok"));
//                cancelButton.setText(messages.getString("cancel"));
//            }

//            @Override
//            public void setSaveButtonListener() {
//                saveButton.addClickListener(new Button.ClickListener() {
//                    @Override
//                    public void buttonClick(Button.ClickEvent event) {
//                        close();
//                        doThisOnOK();
//                    }
//                });
//            }
//
//            @Override
//            public void setCancelButtonListener() {
//                cancelButton.addClickListener(new Button.ClickListener() {
//                    @Override
//                    public void buttonClick(Button.ClickEvent event) {
//                        close();
//                        doThisOnCancel();
//                    }
//                });
//            }
//        };
        add(textLabel);
        add(buttonLeiste);
    }

    public void setModal(boolean b){
//        super.setModal(b);
    }

    public abstract void doThisOnOK();
    public abstract void doThisOnCancel();


}
