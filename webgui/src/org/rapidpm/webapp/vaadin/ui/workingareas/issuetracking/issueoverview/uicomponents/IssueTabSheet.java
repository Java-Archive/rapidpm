package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.ui.TabSheet;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.DetailsCancelButtonClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.DetailsSaveButtonClickListener;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 27.09.12
 * Time: 13:17
 * To change this template use File | Settings | File Templates.
 */
public class IssueTabSheet extends TabSheet implements Internationalizationable{
    final private IssueOverviewScreen screen;
    private IssueDetailsLayout detailsLayout;
    private IssueTableLayout tableLayout;
    private Tab detailsTab;
    private Tab tableTab;

    public IssueTabSheet(final IssueOverviewScreen screen) {
        if (screen == null)
            throw new NullPointerException("Screen must not be null");

        this.screen = screen;
        this.setHeight("100%");
        setComponents();
        doInternationalization();
    }

    private void setComponents() {
        tableLayout = new IssueTableLayout(screen, true);
        detailsLayout = new IssueDetailsLayout(screen, true);
        detailsLayout.addSaveButtonClickListener(new DetailsSaveButtonClickListener(screen, detailsLayout));
        detailsLayout.addCancelButtonClickListener(new DetailsCancelButtonClickListener(detailsLayout));
        tableTab = this.addTab(tableLayout);
        tableTab.setEnabled(false);
        detailsTab = this.addTab(detailsLayout);
        detailsTab.setEnabled(false);
    }

    public void setTableTabOnlyEnabled(boolean value) {
        tableTab.setEnabled(value);
        detailsTab.setEnabled(true);
        if (value) this.setSelectedTab(detailsTab);
        else  this.setSelectedTab(tableTab);
    }

    public void setAllTabsEnabled(boolean value) {
        tableTab.setEnabled(value);
        detailsTab.setEnabled(value);
        this.setSelectedTab(tableTab);
    }

    @Override
    public void doInternationalization() {
        tableTab.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_table"));
        detailsTab.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_detail"));
    }

    public IssueDetailsLayout getDetailsLayout() {
        return detailsLayout;
    }

    public IssueTableLayout getTableLayout() {
        return tableLayout;
    }

}
