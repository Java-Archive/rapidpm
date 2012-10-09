package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.event.MouseEvents;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Componentssetable;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;

import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 14:45
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public abstract class EditablePanel extends Panel implements Internationalizationable, Componentssetable {

    protected Button saveButton = new Button();
    protected Button cancelButton = new Button();
    protected HorizontalLayout buttonsLayout = new HorizontalLayout();
    protected ResourceBundle messagesBundle;

    public EditablePanel(final ResourceBundle messagesBundle){
        this.messagesBundle = messagesBundle;

        addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent event) {
                activate(true);
            }
        });
    }

    public abstract void activate(boolean b);

    @Override
    public abstract void setComponents();

    @Override
    public abstract void doInternationalization();
}
