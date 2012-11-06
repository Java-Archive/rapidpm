//package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.uicomponents;
//
//import com.vaadin.ui.*;
//import org.apache.log4j.Logger;
//import org.rapidpm.persistence.GraphDBFactory;
//import org.rapidpm.persistence.GraphDaoFactory;
//import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
//import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
//import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.modell.SettingsDataContainer;
//
///**
//* Created with IntelliJ IDEA.
//* User: Alvin
//* Date: 05.11.12
//* Time: 09:54
//* To change this template use File | Settings | File Templates.
//*/
//public class SettingsDeleteWindow extends Window implements Internationalizationable {
//    private static Logger logger = Logger.getLogger(SettingsDeleteWindow.class);
//
//    private final IssueOverviewScreen screen;
//    private final Class aClass;
//    private SettingsDataContainer container;
//    private Label deleteLabel;
//    private Button yesButton;
//    private Button noButton;
//    private HorizontalLayout buttonLayout;
//    private Table table;
//
//    private final SettingsDeleteWindow self = this;
//
//    public SettingsDeleteWindow(final IssueOverviewScreen screen, final Class aClass) {
//        if (screen == null)
//            throw new NullPointerException("Screen must not be NULL!");
//        if (aClass == null)
//            throw new NullPointerException("Class must not be NULL!");
//        this.screen = screen;
//        this.aClass = aClass;
//        this.setModal(true);
//        setComponentens();
//        doInternationalization();
//    }
//
//    private void setComponentens() {
//        deleteLabel = new Label();
//        deleteLabel.setWidth("100%");
//        addComponent(deleteLabel);
//
//        container = new SettingsDataContainer(aClass);
//
//        table = new Table("Select item connected Issues get assigned to");
//        table.setContainerDataSource(container);
//        table.setWidth("100%");
//        table.setImmediate(true);
//        table.setEditable(false);
//        table.setSelectable(true);
//        table.setPageLength(10);
//
//        buttonLayout = new HorizontalLayout();
//        buttonLayout.setMargin(true);
//        buttonLayout.setSpacing(true);
//
//        yesButton = new Button();
//        yesButton.setWidth("100%");
//        yesButton.addClickListener(new YesButtonClickListener());
//        buttonLayout.addComponent(yesButton);
//        buttonLayout.setExpandRatio(yesButton, 1.0f);
//
//        noButton = new Button();
//        noButton.setWidth("100%");
//        noButton.addClickListener(new NoButtonClickListener());
//        buttonLayout.addComponent(noButton);
//        buttonLayout.setExpandRatio(noButton, 1.0f);
//
//
//        addComponent(buttonLayout);
//    }
//
//
//    @Override
//    public void doInternationalization() {
//        setCaption(screen.getMessagesBundle().getString("issuetracking_settings_deletewindow"));
//
//        deleteLabel.setCaption(screen.getMessagesBundle().getString("issuetracking_settings_delete_question"));
//        deleteLabel.setValue(contentTable.getItem(contentTable.getValue()).toString());
//
//        yesButton.setCaption(screen.getMessagesBundle().getString("yes"));
//        noButton.setCaption(screen.getMessagesBundle().getString("no"));
//    }
//
//
//
//    private class YesButtonClickListener implements Button.ClickListener {
//
//        @Override
//        public void buttonClick(Button.ClickEvent event) {
//            Object item = contentTable.getValue();
//            contentTable.select(contentTable.getNullSelectionItemId());
//            contentTable.removeItem(item);
//            self.close();
//        }
//    }
//
//    private class NoButtonClickListener implements Button.ClickListener {
//
//        @Override
//        public void buttonClick(Button.ClickEvent event) {
//            self.close();
//        }
//    }
//}
