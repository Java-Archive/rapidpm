package org.rapidpm.webapp.vaadin.ui.windows;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 23:44
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.rapidpm.webapp.vaadin.BaseUI;
import org.rapidpm.webapp.vaadin.ui.Languages;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;

import java.util.Arrays;

public class LoginMask extends VerticalLayout {

    private BaseUI ui;
    private TextField usernameField;
    private PasswordField passwordField;
    private ComboBox languageBox;
    private RapidPanel frame;

    private FormLayout loginLayout;
    private Button loginButton;

    public LoginMask(final BaseUI ui) {
        this.ui = ui;
        setSizeFull();
        frame = new RapidPanel();
        loginLayout = new FormLayout();
        usernameField = new TextField("username");
        usernameField.focus();
        passwordField = new PasswordField("password");
        loginButton = new Button("Login");
        languageBox = new ComboBox("Language", Arrays.asList(Languages.values()));
        loginButton.addClickListener(buttonClickEvent -> {
            try {
                final String username = usernameField.getValue();
                final String password = passwordField.getValue();
//                ui.localization(languageBox.getValue());
//                ui.authentication(username, password);
            } catch (Exception e) {
                Notification.show("Login failed...");
                e.printStackTrace();
            }
        });
        languageBox.setValue(Languages.GERMAN);
        loginLayout.add(usernameField);
        loginLayout.add(passwordField);

        frame.add(loginLayout);
        frame.add(loginButton);
        frame.add(languageBox);
        setComponents();
    }

    public void setComponents() {
        add(frame);
        setAlignItems(Alignment.CENTER);
    }

}
