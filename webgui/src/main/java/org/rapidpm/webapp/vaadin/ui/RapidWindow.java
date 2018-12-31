package org.rapidpm.webapp.vaadin.ui;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 23.11.12
 * Time: 13:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RapidWindow {

    private final VerticalLayout contentLayout = new VerticalLayout();

    public RapidWindow(){
        contentLayout.setSpacing(true);
        contentLayout.setMargin(true);
        add(contentLayout);
//        setModal(true);
    }

    public void add(final Component component){
        contentLayout.add(component);
    }

    public VerticalLayout getContentLayout() {
        return contentLayout;
    }
}
