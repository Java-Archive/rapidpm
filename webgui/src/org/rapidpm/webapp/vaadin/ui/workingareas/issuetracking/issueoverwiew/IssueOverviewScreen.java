package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverwiew;


import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;


/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 20.09.12
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class IssueOverviewScreen extends Screen{

    private HorizontalSplitPanel hSplitPanel;
    private Panel treePanel;
    private Tree issueTree;

    private Button saveButton;
    private Button cancelButton;
    private HorizontalLayout buttonLayout;


    public IssueOverviewScreen(MainUI ui) {
        super(ui);
        hSplitPanel = new HorizontalSplitPanel();
        hSplitPanel.setSplitPosition(40, Unit.PERCENTAGE);
        hSplitPanel.setSizeFull();
        treePanel = new Panel();
        treePanel.setHeight("200px");
        issueTree = new Tree("IssueTree");
        saveButton = new Button();
        cancelButton = new Button();
        buttonLayout = new HorizontalLayout();
        doInternationalization();
        setComponents();
    }

    private void fillTreeWithIssues() {

    }

    @Override
    public void setComponents() {

        treePanel.addComponent(issueTree);
        hSplitPanel.addComponent(treePanel);
        buttonLayout.addComponent(saveButton);
        buttonLayout.addComponent(cancelButton);
        hSplitPanel.addComponent(buttonLayout);
        addComponent(hSplitPanel);
    }

    @Override
    public void doInternationalization() {
        saveButton.setCaption(messagesBundle.getString("save"));
        cancelButton.setCaption(messagesBundle.getString("cancel"));
    }
}
