package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview;


import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueTabSheet;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueTreeLayout;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 20.09.12
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class IssueOverviewScreen extends Screen{

    private HorizontalSplitPanel hSplitPanel;
    private IssueTreeLayout treeLayout;
    private IssueTabSheet issueTabSheet;

    private Button saveButton;
    private Button cancelButton;
    private HorizontalLayout buttonLayout;


    public IssueOverviewScreen(MainUI ui) {
        super(ui);
        this.setSizeFull();
        hSplitPanel = new HorizontalSplitPanel();
        hSplitPanel.setSplitPosition(30, Unit.PERCENTAGE);
        hSplitPanel.setSizeFull();
        issueTabSheet = new IssueTabSheet(this);
        treeLayout = new IssueTreeLayout(this, issueTabSheet);
        saveButton = new Button();
        cancelButton = new Button();
        buttonLayout = new HorizontalLayout();
        doInternationalization();
        setComponents();
    }


    @Override
    public void setComponents() {
        hSplitPanel.setFirstComponent(treeLayout);
        hSplitPanel.setSecondComponent(issueTabSheet);
        addComponent(hSplitPanel);
    }

    @Override
    public void doInternationalization() {
        saveButton.setCaption(messagesBundle.getString("save"));
        cancelButton.setCaption(messagesBundle.getString("cancel"));
    }
}
