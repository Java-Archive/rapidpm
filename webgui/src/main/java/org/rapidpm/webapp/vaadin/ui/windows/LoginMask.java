package org.rapidpm.webapp.vaadin.ui.windows;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 23:44
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.BaseUI;
import org.rapidpm.webapp.vaadin.ui.Languages;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;

import java.util.Arrays;

public class LoginMask extends VerticalLayout {

  //    private BaseUI ui;
  private TextField usernameField;
  private PasswordField passwordField;
  private ComboBox languageBox;
  private RapidPanel frame;

  private FormLayout loginLayout;
  private Button loginButton;

  public LoginMask(final BaseUI ui) {
//        this.ui = ui;
    setSizeFull();
//        initUI();
    frame = new RapidPanel();
    frame.setSizeUndefined();
    loginLayout = new FormLayout();
    usernameField = new TextField("username");
    usernameField.focus();
    usernameField.setValue("marco.ebbinghaus");
    passwordField = new PasswordField("password");
    passwordField.setValue("geheim");
    loginButton = new Button("Login");
    loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    languageBox = new ComboBox("Language", Arrays.asList(Languages.values()));
    loginLayout.setCaption("Please enter your username and password");
    loginButton.addClickListener(clickEvent -> {
      try {
        final String username = usernameField.getValue();
        final String password = passwordField.getValue();
        ui.localization(languageBox.getValue());
        ui.authentication(username, password);
      } catch (Exception e) {
        Notification.show("Login failed...");
        e.printStackTrace();
      }
    });
    languageBox.setImmediate(true);
    languageBox.setValue(Languages.GERMAN);
    languageBox.setNullSelectionAllowed(false);
    languageBox.setTextInputAllowed(false);

    loginLayout.addComponent(usernameField);
    loginLayout.addComponent(passwordField);

    frame.addComponent(loginLayout);
    frame.addComponent(loginButton);
    frame.addComponent(languageBox);

    setComponents();
  }

  public void setComponents() {
    addComponent(frame);
    setComponentAlignment(frame, Alignment.MIDDLE_CENTER);
  }

}
