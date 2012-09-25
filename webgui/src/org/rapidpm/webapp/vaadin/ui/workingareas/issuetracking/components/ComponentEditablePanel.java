package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.components;

import com.vaadin.event.MouseEvents;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;

import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 25.09.12
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public abstract class ComponentEditablePanel extends Panel{
    protected Button saveButton;
    protected Button cancelButton;
    protected ResourceBundle messages;

    protected FormLayout componentsLayout;
    protected HorizontalLayout buttonLayout;

    public ComponentEditablePanel(final Screen screen){
        componentsLayout = buildForm();
        buttonLayout = new HorizontalLayout();
        messages = screen.getMessagesBundle();

        saveButton = new Button();
        saveButton.setCaption(messages.getString("save"));
        saveButton.addClickListener(addSaveButtonClickListener(this));

        cancelButton = new Button();
        cancelButton.setCaption(messages.getString("cancel"));
        cancelButton.addClickListener(addCancelButtonClickListener(this));
        this.addClickListener(new MouseEvents.ClickListener() {

            @Override
            public void click(MouseEvents.ClickEvent event) {
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                while (componentIterator.hasNext()) {
                    final Component component = componentIterator.next();
                    if (component instanceof Field) {
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


    protected abstract FormLayout buildForm();

    protected Button.ClickListener addSaveButtonClickListener(ComponentEditablePanel panel) {
        return new CancelButtonStandardClickListener();
    }

    protected Button.ClickListener addCancelButtonClickListener(ComponentEditablePanel panel) {
        return new CancelButtonStandardClickListener();
    }




    private class CancelButtonStandardClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
            while (componentIterator.hasNext()) {
                final Component component = componentIterator.next();
                if (component instanceof Field) {
                    component.setReadOnly(true);
                }
            }
            buttonLayout.setVisible(false);
        }
    }

}
