package org.rapidpm.webapp.vaadin.ui;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 23:44
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import org.rapidpm.webapp.vaadin.BaseApplication;
import org.apache.log4j.Logger;

public class LoginWindow extends Window {
    private static final Logger logger = Logger.getLogger(LoginWindow.class);

    public LoginWindow() {
        super("Authentication required !");
//        initUI();

        final LoginForm loginForm = new LoginForm();
        loginForm.setCaption("Bitte geben Sie Ihre Logindaten ein.");
        loginForm.addListener(new LoginForm.LoginListener() {
            @Override
            public void onLogin(final LoginForm.LoginEvent loginEvent) {
                final BaseApplication app = (BaseApplication) getApplication();
                try {
                    final String username = loginEvent.getLoginParameter("username");
                    final String password = loginEvent.getLoginParameter("password");
                    app.authentication(username, password);
                    close();
                } catch (Exception e) {
                    Notification.show(e.getLocalizedMessage(), Notification.TYPE_ERROR_MESSAGE);
                }
            }
        });
        addComponent(loginForm);
    }
}
