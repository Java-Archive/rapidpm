package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.AddProjectWindow;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 23.11.12
 * Time: 12:40
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class NoProjectsScreen extends Screen {

    private final RapidPanel noProjectsPanel;
    private final HorizontalLayout buttonLayout;
    private final Label descriptionLabel;
    private final Button wizardButton;
    private final Button manualButton;

    public NoProjectsScreen() {

        noProjectsPanel = new RapidPanel();
        descriptionLabel = new Label();
        buttonLayout = new HorizontalLayout();
        wizardButton = new Button();
        manualButton = new Button();

//        setButtonListeners(ui);

        buttonLayout.add(wizardButton);
        buttonLayout.add(manualButton);

        noProjectsPanel.add(descriptionLabel);
        noProjectsPanel.add(buttonLayout);

        doInternationalization();
        setComponents();
    }

//    private void setButtonListeners(final MainUI ui) {
//        wizardButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                Notification.show("TUDU");
//            }
//        });
//
//        manualButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                final AddProjectWindow addProjectWindow = new AddProjectWindow(ui, messagesBundle);
//                addProjectWindow.show();
//            }
//        });
//    }


    public void setComponents() {
        add(noProjectsPanel);
    }

    public void doInternationalization() {
        descriptionLabel.setText(messagesBundle.getString("noprojects"));
        wizardButton.setText(messagesBundle.getString("wizard"));
        manualButton.setText(messagesBundle.getString("manual"));
    }
}
