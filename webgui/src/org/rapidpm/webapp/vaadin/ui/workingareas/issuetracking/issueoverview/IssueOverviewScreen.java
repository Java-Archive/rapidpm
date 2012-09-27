package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview;


import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueTabSheet;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueTablePanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueTreePanel;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 20.09.12
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class IssueOverviewScreen extends Screen{

    private HorizontalSplitPanel hSplitPanel;
    private IssueTreePanel treePanel;
    private IssueTabSheet issueTabSheet;

    private Button saveButton;
    private Button cancelButton;
    private HorizontalLayout buttonLayout;


    public IssueOverviewScreen(MainUI ui) {
        super(ui);
        hSplitPanel = new HorizontalSplitPanel();
        hSplitPanel.setSplitPosition(30, Unit.PERCENTAGE);
        hSplitPanel.setSizeFull();
        issueTabSheet = new IssueTabSheet(this);
        treePanel = new IssueTreePanel(issueTabSheet);
        saveButton = new Button();
        cancelButton = new Button();
        buttonLayout = new HorizontalLayout();
        doInternationalization();
        setComponents();
    }


    @Override
    public void setComponents() {
        hSplitPanel.addComponent(treePanel);
        hSplitPanel.addComponent(issueTabSheet);//detailsPanel);
        addComponent(hSplitPanel);
    }

    @Override
    public void doInternationalization() {
        saveButton.setCaption(messagesBundle.getString("save"));
        cancelButton.setCaption(messagesBundle.getString("cancel"));
    }
}
