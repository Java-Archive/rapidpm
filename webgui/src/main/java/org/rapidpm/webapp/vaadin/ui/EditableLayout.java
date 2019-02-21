package org.rapidpm.webapp.vaadin.ui;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TreeTableHeaderClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.Iterator;
import java.util.Optional;
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

    public EditableLayout(){
        setLayout();
//        this.setStyleName("abc");
        this.setMargin(false);
        messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        saveButton.setText(messages.getString("save"));
        cancelButton.setText(messages.getString("cancel"));
        buttonLayout.add(saveButton);
        buttonLayout.add(cancelButton);
        buttonLayout.setVisible(false);
        componentsLayout = new FormLayout();
        componentsLayout.getElement().addEventListener("click", e -> {
            final Iterator<Component> componentIterator = componentsLayout.getChildren().iterator();
            while(componentIterator.hasNext()){
                final Component component = componentIterator.next();
                if (component instanceof Grid) {
//                        if(!((Grid) component).isEditable()){
//                            ((Grid) component).setEditable(true);
//                        }
                } else if( component instanceof AbstractField){
                    ((AbstractField)component).setReadOnly(false);
                }
            }
            getButtonLayout().ifPresent(buttonLayout -> {
                buttonLayout.setVisible(true);
                buttonLayout.getChildren().forEach(component -> component.setVisible(true));
            });
        });
        add(componentsLayout);
        add(buttonLayout);
    }


    protected abstract void buildForm();

    protected abstract void setLayout();


    public FormLayout getComponentsLayout() {
        return componentsLayout;
    }

    public void setComponentsLayout(FormLayout componentsLayout) {
        this.componentsLayout = componentsLayout;
    }

    public Optional<HorizontalLayout> getButtonLayout() {
        return Optional.of(buttonLayout);
    }

    public void setButtonLayout(HorizontalLayout buttonLayout) {
        this.buttonLayout = buttonLayout;
    }
}
