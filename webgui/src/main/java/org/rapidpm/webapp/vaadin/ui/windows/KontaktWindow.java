package org.rapidpm.webapp.vaadin.ui.windows;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 21:55
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.validator.EmailValidator;
import org.apache.log4j.Logger;
import org.hibernate.validator.internal.metadata.facets.Validatable;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;

import java.util.Iterator;

public class KontaktWindow extends RapidWindow {
    private static final Logger logger = Logger.getLogger(KontaktWindow.class);

    private static final String KONTAKT_HTML_SNIPPET = "RapidPM HQ</br>Tannenstr. 9</br>82049 Pullach</br></br></br>";

    public KontaktWindow() {
//        setText("Kontaktinfo");
        final Label kontaktLabel = new Label(KONTAKT_HTML_SNIPPET);
//        kontaktLabel.setStyleName("kontakt");
        add(kontaktLabel);

        final FormLayout form = new FormLayout();
//        form.setText("Kontaktformular");
        final TextField nameTextField = new TextField("Name");
        nameTextField.setRequired(true);
        form.add(nameTextField);
        final TextField emailTextField = new TextField("E-Mail");
        emailTextField.setRequired(true);
//        emailTextField.addValidator(new EmailValidator("Ungültige E-Mail-Adresse"));
        form.add(emailTextField);
        final TextArea textTextField = new TextArea("Text");
        textTextField.setRequired(true);
        form.add(textTextField);
//        form.add(new Button("Absenden", new Button.ClickListener() {
//            @Override
//            public void buttonClick(final Button.ClickEvent clickEvent) {
//                try {
//                    // alle Kontaktfelder validieren
//                    final Iterator<Component> componentIterator = form.getComponentIterator();
//                    while (componentIterator.hasNext()) {
//                        final Component component = componentIterator.next();
//                        if (component instanceof Validatable) {
//                            final Validatable validatable = (Validatable) component;
//                            validatable.validate();
//                        }
//                    }
//                    // TODO Formular absenden
////                    getApplication().getMainWindow().showNotification("Kontaktformular abgeschickt",
////                            "Empfänger: " + emailTextField.getValue(), Notification.TYPE_HUMANIZED_MESSAGE);
//                    Notification.show("Kontaktformular abgeschickt Empfänger: " + emailTextField.getValue());
//                    close();
//                } catch (Validator.InvalidValueException e) {
////                    getApplication().getMainWindow().showNotification("Fehler",
////                            "Bitte überprüfen Sie Ihre Eingaben", Notification.TYPE_ERROR_MESSAGE);
//                    Notification.show("Bitte überprüfen Sie Ihre Eingaben");
//                }
//            }
//        }));
        add(form);

//        setWidth(400, Unit.PIXELS);
//        center();
    }


}
