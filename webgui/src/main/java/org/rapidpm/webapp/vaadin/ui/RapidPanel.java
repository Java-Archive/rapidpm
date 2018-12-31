package org.rapidpm.webapp.vaadin.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 23.11.12
 * Time: 13:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
@Tag("RapidPanel")
public class RapidPanel extends Div {

    private final VerticalLayout contentLayout = new VerticalLayout();

    public RapidPanel(){
        contentLayout.setSpacing(true);
        contentLayout.setMargin(true);
        add(contentLayout);
        contentLayout.removeAll();
    }

    protected void turnEditableDesignOn(boolean b) {
//        if(b){
//            contentLayout.setStyleName("abc");
//        } else {
//            contentLayout.setStyleName(null);
//        }
    }

    public void addComponent(final Component component){
        contentLayout.add(component);
    }

    public void removeAllComponents(){
        contentLayout.removeAll();
    }

    public VerticalLayout getContentLayout() {
        return contentLayout;
    }
}
