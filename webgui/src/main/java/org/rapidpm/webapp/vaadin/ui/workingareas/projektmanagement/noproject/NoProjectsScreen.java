package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
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

  public NoProjectsScreen(final MainUI ui) {
    super(ui);

    noProjectsPanel = new RapidPanel();
    descriptionLabel = new Label();
    buttonLayout = new HorizontalLayout();
    wizardButton = new Button();
    manualButton = new Button();

    setButtonListeners(ui);

    buttonLayout.addComponent(wizardButton);
    buttonLayout.addComponent(manualButton);

    noProjectsPanel.addComponent(descriptionLabel);
    noProjectsPanel.addComponent(buttonLayout);

    doInternationalization();
    setComponents();
  }

  private void setButtonListeners(final MainUI ui) {
    wizardButton.addClickListener(new Button.ClickListener() {
      @Override
      public void buttonClick(Button.ClickEvent event) {
        Notification.show("TUDU");
      }
    });

    manualButton.addClickListener(new Button.ClickListener() {
      @Override
      public void buttonClick(Button.ClickEvent event) {
        final AddProjectWindow addProjectWindow = new AddProjectWindow(ui, messagesBundle);
        addProjectWindow.show();
      }
    });
  }

  @Override
  public void doInternationalization() {
    descriptionLabel.setValue(messagesBundle.getString("noprojects"));
    wizardButton.setCaption(messagesBundle.getString("wizard"));
    manualButton.setCaption(messagesBundle.getString("manual"));
  }

  @Override
  public void setComponents() {
    addComponent(noProjectsPanel);
  }
}
