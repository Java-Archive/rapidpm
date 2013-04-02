package org.rapidpm.webapp.vaadin.ui.workingareas.suche;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.SaveCancelButtonLeiste;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 02.04.13
 * Time: 11:54
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class AddSearchPatternWindow extends RapidWindow {
    private FormLayout fieldLayout;
    private TextField nameField;
    private TextField vaterField;
    private TextArea syntaxBox;
    private SaveCancelButtonLeiste buttonLeiste;

    public AddSearchPatternWindow(){
        setWidth("500px");
        setCaption("Suchmuster hinzufügen/editieren");
        nameField = new TextField("Name");
        vaterField = new TextField("Vater-Pattern (optional)");
        syntaxBox = new TextArea("Syntax");

        nameField.setSizeFull();
        vaterField.setSizeFull();
        syntaxBox.setSizeFull();
        buttonLeiste = new SaveCancelButtonLeiste() {
            @Override
            public void doInternationalization() {
                saveButton.setCaption("Speichern");
                cancelButton.setCaption("Abbrechen");
            }

            @Override
            public void setSaveButtonListener() {
                saveButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(final Button.ClickEvent clickEvent) {
                        AddSearchPatternWindow.this.close();
                    }
                });
            }

            @Override
            public void setCancelButtonListener() {
                cancelButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(final Button.ClickEvent clickEvent) {
                        AddSearchPatternWindow.this.close();
                    }
                });
            }
        };

        fieldLayout = new FormLayout(nameField, vaterField, syntaxBox);
        fieldLayout.setSizeFull();
        addComponent(fieldLayout);
        addComponent(buttonLeiste);
    }
}
