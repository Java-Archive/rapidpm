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

    private final HorizontalSplitPanel hSplitPanel;
    private final IssueTreeLayout treeLayout;
    private final IssueTabSheet issueTabSheet;


    public IssueOverviewScreen(final MainUI ui) {
        super(ui);
        this.setSizeFull();
        this.activeVerticalFullScreenSize(true);
        hSplitPanel = new HorizontalSplitPanel();
        hSplitPanel.setSplitPosition(25, Unit.PERCENTAGE);
        issueTabSheet = new IssueTabSheet(this);
        treeLayout = new IssueTreeLayout(this, issueTabSheet);
        doInternationalization();
        setComponents();
    }

    public IssueTabSheet getIssueTabSheet() {
        return issueTabSheet;
    }

    public IssueTreeLayout getIssueTreeLayout() {
        return treeLayout;
    }

    @Override
    public void setComponents() {
        hSplitPanel.setFirstComponent(treeLayout);
        hSplitPanel.setSecondComponent(issueTabSheet);
        addComponent(hSplitPanel);
    }

    @Override
    public void doInternationalization() {
    }
}
