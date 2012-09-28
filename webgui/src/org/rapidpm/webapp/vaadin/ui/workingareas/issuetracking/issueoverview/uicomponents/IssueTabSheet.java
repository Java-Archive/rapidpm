package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.ui.TabSheet;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 27.09.12
 * Time: 13:17
 * To change this template use File | Settings | File Templates.
 */
public class IssueTabSheet extends TabSheet implements Internationalizationable{
    final private IssueOverviewScreen screen;
    private IssueDetailsPanel detailsPanel;
    private IssueTablePanel tablePanel;
    private Tab detailsTab;
    private Tab tableTab;

    public IssueTabSheet(final IssueOverviewScreen screen) {
        this.screen = screen;
        this.setSizeFull();
        this.setImmediate(true);
        setComponents();
        doInternationalization();
    }

    private void setComponents() {
        tablePanel = new IssueTablePanel(screen);
        detailsPanel = new IssueDetailsPanel(screen);
        tableTab = this.addTab(tablePanel);
        detailsTab = this.addTab(detailsPanel);
    }

    public boolean isTableTabDisabled() {
        return !tableTab.isEnabled();
    }

    public void disableTableTab(boolean value) {
        tableTab.setEnabled(!value);
        if (value) this.setSelectedTab(detailsTab);
        else  this.setSelectedTab(tableTab);
    }

    @Override
    public void doInternationalization() {
        tableTab.setCaption(screen.getMessagesBundle().getString("issue_table"));
        detailsTab.setCaption(screen.getMessagesBundle().getString("issue_detail"));
    }

    public IssueDetailsPanel getDetailsPanel() {
        return detailsPanel;
    }

    public IssueTablePanel getTablePanel() {
        return tablePanel;
    }

}
