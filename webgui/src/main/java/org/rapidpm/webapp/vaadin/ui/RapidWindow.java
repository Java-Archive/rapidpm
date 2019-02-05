package org.rapidpm.webapp.vaadin.ui;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 23.11.12
 * Time: 13:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RapidWindow extends Dialog {

    private final VerticalLayout contentLayout = new VerticalLayout();

    public RapidWindow(){
//        setModal(true);
    }

    public VerticalLayout getContentLayout() {
        return contentLayout;
    }
}
