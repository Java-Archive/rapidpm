package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueedit;

import com.vaadin.ui.Window;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueedit.uicomponents.IssueDetailsPanel;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 26.09.12
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class IssueEditWindow extends Window implements Internationalizationable{

    private IssueDetailsPanel detailsPanel;

    public IssueEditWindow(IssueOverviewScreen screen) {
        this.setModal(true);
        detailsPanel = new IssueDetailsPanel(screen);
        setComponents();
    }

    private void setComponents() {
        addComponent(detailsPanel);
    }

    @Override
    public void doInternationalization() {

    }
}
