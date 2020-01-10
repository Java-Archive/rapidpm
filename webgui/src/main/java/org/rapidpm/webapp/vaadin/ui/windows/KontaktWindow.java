package org.rapidpm.webapp.vaadin.ui.windows;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 21:55
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;

public class KontaktWindow extends RapidWindow {

    private static final String KONTAKT_HTML_SNIPPET = "RapidPM HQ</br>Tannenstr. 9</br>82049 Pullach</br></br></br>";

    public KontaktWindow() {
        final Label kontaktLabel = new Label(KONTAKT_HTML_SNIPPET);
        add(kontaktLabel);
        final FormLayout form = new FormLayout();
        final TextField nameTextField = new TextField("Name");
        nameTextField.setRequired(true);
        form.add(nameTextField);
        final TextField emailTextField = new TextField("E-Mail");
        emailTextField.setRequired(true);
        form.add(emailTextField);
        final TextArea textTextField = new TextArea("Text");
        textTextField.setRequired(true);
        form.add(textTextField);
        add(form);
    }
}
