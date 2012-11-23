package org.rapidpm.webapp.vaadin.ui;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 23.11.12
 * Time: 13:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RapidPanel extends Panel {

    private final VerticalLayout contentLayout = new VerticalLayout();

    public RapidPanel(){
        contentLayout.setSpacing(true);
        contentLayout.setMargin(true);
        setContent(contentLayout);
    }

    public void addComponent(final Component component){
        contentLayout.addComponent(component);
    }

}
