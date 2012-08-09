package org.rapidpm.webapp.vaadin.ui;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 21:55
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.terminal.gwt.client.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;

import java.util.Iterator;

public class KontaktWindow extends Window {
    private static final Logger logger = Logger.getLogger(KontaktWindow.class);

    private static final String KONTAKT_HTML_SNIPPET = "RapidPM HQ</br>Tannenstr. 9</br>82049 Pullach</br></br></br>";

    public KontaktWindow() {
        setCaption("Kontaktinfo");
        final Label kontaktLabel = new Label(KONTAKT_HTML_SNIPPET, ContentMode.XHTML);
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
        form.addComponent(new Button("Absenden", new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
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
//                    getApplication().getMainWindow().showNotification("Kontaktformular abgeschickt",
//                            "Empfänger: " + emailTextField.getValue(), Notification.TYPE_HUMANIZED_MESSAGE);
                    Notification.show("Kontaktformular abgeschickt Empfänger: " + emailTextField.getValue(), Notification.TYPE_HUMANIZED_MESSAGE);
                    close();
                } catch (Validator.InvalidValueException e) {
//                    getApplication().getMainWindow().showNotification("Fehler",
//                            "Bitte überprüfen Sie Ihre Eingaben", Notification.TYPE_ERROR_MESSAGE);
                    Notification.show("Bitte überprüfen Sie Ihre Eingaben", Notification.TYPE_ERROR_MESSAGE);
                }
            }
        }));
        addComponent(form);

        setWidth(400, Unit.PIXELS);
        center();
    }


}
