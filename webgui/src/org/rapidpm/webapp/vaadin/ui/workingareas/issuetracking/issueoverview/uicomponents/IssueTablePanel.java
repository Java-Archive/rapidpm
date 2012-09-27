package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;


import com.vaadin.data.Item;
import com.vaadin.ui.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.windows.KontaktWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.components.ComponentEditablePanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 26.09.12
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class IssueTablePanel extends ComponentEditablePanel {

    private IssueOverviewScreen screen;

    private Table issueTable;

    public IssueTablePanel(IssueOverviewScreen screen) {
        super(screen);
        this.screen = screen;
        this.setSizeFull();
    }

    @Override
    protected AbstractOrderedLayout buildForm() {
        VerticalLayout componentsLayout = new VerticalLayout();

        issueTable = new Table();
        issueTable.setColumnCollapsingAllowed(true);
        issueTable.setSizeFull();
        issueTable.setImmediate(true);
        issueTable.setSelectable(true);

        String[] headerNames = new String[] {"ID", "Bezeichnung", "Komponente"};
        for (String name : headerNames) {
            issueTable.addContainerProperty(name, String.class, "");
        }

        for (int i=0; i<5;i++) {
            issueTable.addItem(new Object[] {"RPM-" + 1 + i, "Be", "Ko"}, i);
        }

        componentsLayout.addComponent(issueTable);

        return componentsLayout;
    }

    public void setPropertiesFromIssueList(List<IssueBase> issue) {

    }
}
