package org.rapidpm.webapp.vaadin.ui.windows;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 23:44
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.BaseUI;
import org.rapidpm.webapp.vaadin.ui.Languages;

import java.util.Arrays;

public class LoginWindow extends Window {
    private static final Logger logger = Logger.getLogger(LoginWindow.class);
    private BaseUI ui;
    public LoginWindow(BaseUI r) {
        super("Authentication required !");
        setWidth("300px");
        this.ui = r;
//        initUI();

        final LoginForm loginForm = new LoginForm();
        final ComboBox languageBox = new ComboBox("Language", Arrays.asList(Languages.values()));
        loginForm.setCaption("Please enter your username and password");
        loginForm.addLoginListener(new LoginForm.LoginListener() {
            @Override
            public void onLogin(final LoginForm.LoginEvent loginEvent) {
                //final BaseUI app = (BaseUI) getRoot();
                try {
                    final String username = loginEvent.getLoginParameter("username");
                    final String password = loginEvent.getLoginParameter("password");
                    ui.localization(languageBox.getValue());
                    ui.authentication(username, password);
                    close();
                } catch (Exception e) {
                    Notification.show("Login failed...");
                }
            }
        });
        languageBox.setImmediate(true);
        languageBox.setValue(Languages.GERMAN);
        languageBox.setNullSelectionAllowed(false);
        languageBox.setTextInputAllowed(false);
        addComponent(loginForm);
        addComponent(languageBox);
    }
}
