package org.rapidpm.webapp.vaadin.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Element;
import elemental.json.JsonObject;
import org.rapidpm.webapp.vaadin.ui.workingareas.Componentssetable;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;

import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 18.09.12
 * Time: 14:45
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public abstract class EditableRapidPanel extends RapidPanel implements Internationalizationable, Componentssetable {

    protected Button saveButton = new Button();
    protected Button cancelButton = new Button();

    protected FormLayout formLayout = new FormLayout();
    protected HorizontalLayout buttonsLayout = new HorizontalLayout();
    protected ResourceBundle messagesBundle;

    public EditableRapidPanel(final ResourceBundle messagesBundle){
        setSizeFull();
        this.messagesBundle = messagesBundle;
        turnEditableDesignOn(true);
        formLayout.setWidthFull();
        formLayout.getElement().addEventListener("click", event -> {
            activate(true);
        });
    }

    public abstract void activate(boolean b);

    @Override
    public abstract void setComponents();

    @Override
    public abstract void doInternationalization();
}
