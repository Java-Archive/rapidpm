package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.components;

import com.vaadin.event.LayoutEvents;
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
public abstract class ComponentEditableVLayout extends VerticalLayout{
    protected Button saveButton;
    protected Button cancelButton;
    protected ResourceBundle messages;

    protected AbstractOrderedLayout componentsLayout;
    protected HorizontalLayout buttonLayout;

    public ComponentEditableVLayout(final Screen screen){
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        componentsLayout = buildForm();
        componentsLayout.setSizeFull();
        buttonLayout = new HorizontalLayout();
        messages = screen.getMessagesBundle();

        saveButton = new Button();
        saveButton.setCaption(messages.getString("save"));
        saveButton.addClickListener(addSaveButtonClickListener(this));

        cancelButton = new Button();
        cancelButton.setCaption(messages.getString("cancel"));
        cancelButton.addClickListener(addCancelButtonClickListener(this));
        this.addLayoutClickListener(new LayoutMouseClickListener());

        buttonLayout.addComponent(saveButton);
        buttonLayout.addComponent(cancelButton);
        buttonLayout.setVisible(false);
        addComponent(componentsLayout);
        addComponent(buttonLayout);
    }


    protected abstract AbstractOrderedLayout buildForm();

    protected Button.ClickListener addSaveButtonClickListener(ComponentEditableVLayout layout) {
        return new CancelButtonStandardClickListener();
    }

    protected Button.ClickListener addCancelButtonClickListener(ComponentEditableVLayout layout) {
        return new CancelButtonStandardClickListener();
    }

    public void setLayoutReadOnly(boolean readOnly) {
        iterateLayoutReadOnly(readOnly, componentsLayout);
    }

    private void iterateLayoutReadOnly(boolean readOnly, AbstractOrderedLayout layout) {
        final Iterator<Component> componentIterator = layout.getComponentIterator();
        while (componentIterator.hasNext()) {
            final Component component = componentIterator.next();
            if (component instanceof AbstractOrderedLayout) {
                iterateLayoutReadOnly(readOnly, (AbstractOrderedLayout) component);
            } else if (component instanceof Field) {
                component.setReadOnly(readOnly);
            }
        }
        buttonLayout.setVisible(!readOnly);
    }

    private class LayoutMouseClickListener implements LayoutEvents.LayoutClickListener {

        @Override
        public void layoutClick(LayoutEvents.LayoutClickEvent event) {
            setLayoutReadOnly(false);
        }
    }


    private class CancelButtonStandardClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            setLayoutReadOnly(true);
        }
    }

}
