package org.rapidpm.webapp.vaadin.ui.windows;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 23:44
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
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
                    String sql = "select count(*) as count from planningunit";
                    SqlRow row = Ebean.createSqlQuery(sql).findUnique();

                    Integer i = row.getInteger("count");
                    System.out.println("Got "+i+" - DataSource good!!!");
                    final String username = loginEvent.getLoginParameter("username");
                    final String password = loginEvent.getLoginParameter("password");
                    ui.localization(languageBox.getValue());
                    ui.authentication(username, password);
                    close();
                } catch (Exception e) {
                    Notification.show("Login failed...");
                    e.printStackTrace();
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
