package org.rapidpm.webapp.vaadin.ui.windows;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 23:44
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.BaseRoot;

public class LoginWindow extends Window {
    private static final Logger logger = Logger.getLogger(LoginWindow.class);
    private BaseRoot root;
    public LoginWindow(BaseRoot r) {
        super("Authentication required !");
        setWidth("300px");
        this.root = r;
//        initUI();

        final LoginForm loginForm = new LoginForm();
        loginForm.setCaption("Bitte geben Sie Ihre Logindaten ein.");
        loginForm.addListener(new LoginForm.LoginListener() {
            @Override
            public void onLogin(final LoginForm.LoginEvent loginEvent) {
                //final BaseRoot app = (BaseRoot) getRoot();
                try {
                    final String username = loginEvent.getLoginParameter("username");
                    final String password = loginEvent.getLoginParameter("password");
                    root.authentication(username, password);
                    close();
                } catch (Exception e) {
                    Notification.show(e.getLocalizedMessage(), Notification.TYPE_ERROR_MESSAGE);
                }
            }
        });
        addComponent(loginForm);
    }
}
