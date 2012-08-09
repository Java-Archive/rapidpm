package org.rapidpm.webapp.vaadin.ui.workingareas.demo;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.12
 * Time: 01:44
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.vaadin.ui.*;
import org.apache.log4j.Logger;

public class DemoWorkingArea extends HorizontalSplitPanel {
    private static final Logger logger = Logger.getLogger(DemoWorkingArea.class);


    public DemoWorkingArea() {
        final HorizontalSplitPanel horizontalSplitPanel = this;
        //        horizontalSplitPanel.setHeight(-1);
        horizontalSplitPanel.setSplitPosition(25); //%


        final Accordion accordion = new Accordion();
        accordion.setSizeFull();
        accordion.addTab(new Label("Tab 001 conten .. "), "Actions 001", null);
        accordion.addTab(new Label("Tab 002 conten .. "), "Actions 002", null);
        accordion.addTab(new Label("Tab 003 conten .. "), "Actions 003", null);
        horizontalSplitPanel.addComponent(accordion); //left


        // Tab 1 content
        VerticalLayout l1 = new VerticalLayout();
        l1.setMargin(true);
        l1.addComponent(new Label("There are no previously saved actions."));
        // Tab 2 content
        VerticalLayout l2 = new VerticalLayout();
        l2.setMargin(true);
        l2.addComponent(new Label("There are no saved notes."));
        // Tab 3 content
        VerticalLayout l3 = new VerticalLayout();
        l3.setMargin(true);
        l3.addComponent(new Label("There are currently no issues."));
        // Tab 4 content
        VerticalLayout l4 = new VerticalLayout();
        l4.setMargin(true);
        l4.addComponent(new Label("There are no comments."));
        // Tab 5 content
        VerticalLayout l5 = new VerticalLayout();
        l5.setMargin(true);
        l5.addComponent(new Label("There is no new feedback."));

        final TabSheet t = new TabSheet();

        final TabSheet.Tab saved = t.addTab(l1, "Übersicht");
        saved.setClosable(true);
        final TabSheet.Tab notes = t.addTab(l2, "TagCloud");
        notes.setClosable(true);
        final TabSheet.Tab issues = t.addTab(l3, "Clustered");
        issues.setClosable(true);
        final TabSheet.Tab comments = t.addTab(l4, "Ranking");
        comments.setClosable(true);
        final TabSheet.Tab feedback = t.addTab(l5, "Feedback");
        horizontalSplitPanel.addComponent(t); //right
    }


}
