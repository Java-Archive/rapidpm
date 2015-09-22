package org.rapidpm.webapp.vaadin.ui.windows;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 21:55
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;

import java.util.Iterator;

public class KontaktWindow extends RapidWindow {

  private static final String KONTAKT_HTML_SNIPPET = "RapidPM HQ</br>Tannenstr. 9</br>82049 Pullach</br></br></br>";

  public KontaktWindow() {
    setCaption("Kontaktinfo");
    final Label kontaktLabel = new Label(KONTAKT_HTML_SNIPPET, ContentMode.HTML);
    kontaktLabel.setStyleName("kontakt");
    addComponent(kontaktLabel);

    final FormLayout form = new FormLayout();
    form.setCaption("Kontaktformular");
    final TextField nameTextField = new TextField("Name");
    nameTextField.setRequired(true);
    form.addComponent(nameTextField);
    final TextField emailTextField = new TextField("E-Mail");
    emailTextField.setRequired(true);
    emailTextField.addValidator(new EmailValidator("Ungültige E-Mail-Adresse"));
    form.addComponent(emailTextField);
    final TextArea textTextField = new TextArea("Text");
    textTextField.setRequired(true);
    form.addComponent(textTextField);
    form.addComponent(new Button("Absenden", clickEvent -> {
      try {
        // alle Kontaktfelder validieren
        final Iterator<Component> componentIterator = form.getComponentIterator();
        while (componentIterator.hasNext()) {
          final Component component = componentIterator.next();
          if (component instanceof Validatable) {
            final Validatable validatable = (Validatable) component;
            validatable.validate();
          }
        }
        // TODO Formular absenden
        Notification.show("Kontaktformular abgeschickt Empfänger: " + emailTextField.getValue());
        close();
      } catch (Validator.InvalidValueException e) {
        Notification.show("Bitte überprüfen Sie Ihre Eingaben");
      }
    }));
    addComponent(form);

    setWidth(400, Unit.PIXELS);
    center();
  }


}
