package org.rapidpm.webapp.vaadin.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
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

    protected FormLayout componentsLayout;
    protected HorizontalLayout buttonLayout = new HorizontalLayout();

    public EditableLayout(final Screen screen, final Component screenPanel){
        setLayout();
//        this.setStyleName("abc");
        this.setMargin(false);
        messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        saveButton.setText(messages.getString("save"));
        cancelButton.setText(messages.getString("cancel"));
//        screenPanel.addClickListener(new MouseEvents.ClickListener() {
//            @Override
//            public void click(MouseEvents.ClickEvent event) {
//                final Iterator<Component> componentIterator = componentsLayout.iterator();
//                while(componentIterator.hasNext()){
//                    final Component component = componentIterator.next();
//
//                    if (component instanceof Table) {
//                        if(!((Table) component).isEditable()){
//                            ((Table) component).setEditable(true);
//                        }
//                    } else if( component instanceof AbstractField){
//                        component.setReadOnly(false);
//                    }
//                }
//                buttonLayout.setVisible(true);
//            }
//        });

        buttonLayout.add(saveButton);
        buttonLayout.add(cancelButton);
        buttonLayout.setVisible(false);
        componentsLayout = new FormLayout();
        add(componentsLayout);
        add(buttonLayout);
    }


    protected abstract void buildForm();

    protected abstract void setLayout();


//    public Layout getComponentsLayout() {
//        return componentsLayout;
//    }
//
//    public void setComponentsLayout(Layout componentsLayout) {
//        this.componentsLayout = componentsLayout;
//    }

    public HorizontalLayout getButtonLayout() {
        return buttonLayout;
    }

    public void setButtonLayout(HorizontalLayout buttonLayout) {
        this.buttonLayout = buttonLayout;
    }
}
