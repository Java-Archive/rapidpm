package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.logic;

import com.vaadin.event.MouseEvents;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ProjektplanungScreen;

import java.util.Iterator;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 09:16
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public abstract class MyFormLayout extends VerticalLayout {

    protected Button saveButton = new Button("Save");
    protected Button cancelButton = new Button("Cancel");

    protected FormLayout componentsLayout = new FormLayout();
    protected HorizontalLayout buttonLayout = new HorizontalLayout();

    public MyFormLayout(final IssueBase issueBase, final ProjektplanungScreen screen, final Panel screenPanel){
        screenPanel.addListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent event) {
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                while(componentIterator.hasNext()){
                    final Component component = componentIterator.next();
                    if( component instanceof Field){
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
