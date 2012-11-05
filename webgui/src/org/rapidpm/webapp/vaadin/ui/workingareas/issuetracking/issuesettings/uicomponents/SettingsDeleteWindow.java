//package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.uicomponents;
//
//import com.vaadin.ui.*;
//import org.apache.log4j.Logger;
//import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
//import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
//
///**
// * Created with IntelliJ IDEA.
// * User: Alvin
// * Date: 05.11.12
// * Time: 09:54
// * To change this template use File | Settings | File Templates.
// */
//public class SettingsDeleteWindow extends Window implements Internationalizationable {
//    private static Logger logger = Logger.getLogger(SettingsDeleteWindow.class);
//
//    private final IssueOverviewScreen screen;
//    private Label deleteLabel;
//    private Button yesButton;
//    private Button noButton;
//    private HorizontalLayout buttonLayout;
//
//    private final SettingsDeleteWindow self = this;
//    private final Table contentTable;
//
//    public SettingsDeleteWindow(final IssueOverviewScreen screen, final Table contentTable) {
//        if (screen == null)
//            throw new NullPointerException("Screen must not be NULL!");
//        if (contentTable == null)
//            throw new NullPointerException("ContentTable must not be NULL!");
//        this.screen = screen;
//        this.contentTable = contentTable;
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
//        deleteLabel.setValue(issueTree.getItemCaption(issueTree.getValue()));
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
//            Object item = issueTree.getValue();
//            issueTree.setValue(issueTree.getParent(item));
//            issueTree.removeItem(item);
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
