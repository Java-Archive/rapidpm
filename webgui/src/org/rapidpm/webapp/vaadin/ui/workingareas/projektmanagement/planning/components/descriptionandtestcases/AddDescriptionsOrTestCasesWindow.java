package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.descriptionandtestcases;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 02.01.13
 * Time: 22:28
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class AddDescriptionsOrTestCasesWindow extends RapidWindow {

    private TextField quantityField;
    private ComboBox comboBox;
    private Button okButton;
    private Button cancelButton;
    private HorizontalLayout buttonLayout;
    private ResourceBundle messages;

    public AddDescriptionsOrTestCasesWindow(final ResourceBundle messages) {
        this.messages = messages;
        buttonLayout = new HorizontalLayout();
        quantityField = new TextField(messages.getString("planning_quantity"));
        quantityField.setValue("1");
        quantityField.focus();
        quantityField.setWidth("40px");
        comboBox = new ComboBox(messages.getString("planning_kind"), Arrays.asList(DescriptionTestcaseEnum.values()));
        comboBox.setNullSelectionAllowed(false);
        comboBox.setTextInputAllowed(false);
        comboBox.setValue(DescriptionTestcaseEnum.DESCRIPTION);
        okButton = new Button(messages.getString("ok"));
        okButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancelButton = new Button(messages.getString("cancel"));
        buttonLayout.addComponents(okButton, cancelButton);

        setButtonClickListeners();

        addComponent(quantityField);
        addComponent(comboBox);
        addComponent(buttonLayout);

        doInternationalization();


    }

    private void setButtonClickListeners() {
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });
    }

    private void doInternationalization() {
        setCaption(messages.getString("add"));
    }
}
