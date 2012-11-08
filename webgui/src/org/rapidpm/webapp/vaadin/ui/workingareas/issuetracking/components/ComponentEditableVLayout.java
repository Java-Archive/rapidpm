package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.components;

import com.vaadin.event.LayoutEvents;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 25.09.12
 * Time: 15:13
 *
 * To change this template use File | Settings | File Templates.
 */
public abstract class ComponentEditableVLayout extends VerticalLayout{
    private static Logger logger = Logger.getLogger(ComponentEditableVLayout.class);

    protected Button saveButton;
    protected Button cancelButton;
    protected ResourceBundle messages;

    protected AbstractOrderedLayout saveableLayout;
    protected AbstractOrderedLayout unSaveableLayout;
    protected HorizontalLayout buttonLayout;
    protected final IssueOverviewScreen screen;

    public ComponentEditableVLayout(final IssueOverviewScreen screen, final boolean readOnlyInit){
        super();
        this.screen = screen;
        messages = screen.getMessagesBundle();
        setMargin(true);
        setSpacing(true);
        saveableLayout = buildSaveableForm();
        if (saveableLayout != null) {
            saveableLayout.setSizeFull();
            addComponent(saveableLayout);
            saveableLayout.addLayoutClickListener(new LayoutMouseClickListener());
        }
        buttonLayout = new HorizontalLayout();
        saveButton = new Button();
        saveButton.setCaption(messages.getString("save"));


        cancelButton = new Button();
        cancelButton.setCaption(messages.getString("cancel"));

        buttonLayout.addComponent(saveButton);
        buttonLayout.addComponent(cancelButton);
        buttonLayout.setSpacing(true);
        //buttonLayout.setMargin(true);
        buttonLayout.setVisible(false);

        addComponent(buttonLayout);
        setLayoutReadOnly(readOnlyInit);

        unSaveableLayout = buildUnsaveableForm();
        if (unSaveableLayout != null) {
            unSaveableLayout.setSizeFull();
            unSaveableLayout.setSpacing(true);
            addComponent(unSaveableLayout);
        }
    }


    protected abstract AbstractOrderedLayout buildSaveableForm();
    protected abstract AbstractOrderedLayout buildUnsaveableForm();

    public void addSaveButtonClickListener(Button.ClickListener listener) {
        saveButton.addClickListener(listener);
    }

    public void addCancelButtonClickListener(Button.ClickListener listener) {
        cancelButton.addClickListener(listener);
    }


    public void setLayoutReadOnly(boolean readOnly) {
        if (saveableLayout != null)
            iterateLayoutReadOnly(readOnly, saveableLayout);
        else
            if (logger.isDebugEnabled())
                logger.debug("No Components present in UnsabeableLayout");
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

}
