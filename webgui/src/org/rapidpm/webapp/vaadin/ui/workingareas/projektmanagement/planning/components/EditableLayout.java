package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components;

import com.vaadin.event.MouseEvents;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 30.08.12
 * Time: 09:16
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public abstract class EditableLayout extends VerticalLayout {

    protected Button saveButton = new Button();
    protected Button cancelButton = new Button();
    protected ResourceBundle messages;

    protected FormLayout componentsLayout = new FormLayout();
    protected HorizontalLayout buttonLayout = new HorizontalLayout();

    public EditableLayout(final ProjektplanungScreen screen, final Panel screenPanel){
        this.setStyleName("abc");
        this.setMargin(false);
        messages = screen.getMessagesBundle();
        saveButton.setCaption(messages.getString("save"));
        cancelButton.setCaption(messages.getString("cancel"));
        screenPanel.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent event) {
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                while(componentIterator.hasNext()){
                    final Component component = componentIterator.next();

                    if (component instanceof Table) {
                        if(!((Table) component).isEditable()){
                            ((Table) component).setEditable(true);
                        }
                    } else if( component instanceof Field){
                        component.setReadOnly(false);
                    }
                }
                buttonLayout.setVisible(true);
            }
        });

        buttonLayout.addComponent(saveButton);
        buttonLayout.addComponent(cancelButton);
        buttonLayout.setVisible(false);
        addComponent(componentsLayout);
        addComponent(buttonLayout);
    }


    protected abstract void buildForm();


    public FormLayout getComponentsLayout() {
        return componentsLayout;
    }

    public void setComponentsLayout(FormLayout componentsLayout) {
        this.componentsLayout = componentsLayout;
    }

    public HorizontalLayout getButtonLayout() {
        return buttonLayout;
    }

    public void setButtonLayout(HorizontalLayout buttonLayout) {
        this.buttonLayout = buttonLayout;
    }
}
