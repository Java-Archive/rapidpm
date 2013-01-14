package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;


import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 26.09.12
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class IssueTableLayout extends ComponentEditableVLayout {

    private final static String CAPTION = "caption";
    private final static String STATUS = "status";
    private final static String PRIORITY = "priority";
    private final static String TYPE = "type";
    private final static String VERSION = "version";
    private final static String STORYPOINTS = "storypoints";
    private final static String SUMMARY = "summary";
    private final static String REPORTER = "reporter";
    private final static String ASSIGNEE = "assignee";
    private final static String DUE_DATE_PLANNED = "dueDate_planned";
    private final static String DUE_DATE_RESOLVED = "dueDate_resolved";
    private final static String DUE_DATE_CLOSED = "dueDate_closed";



    private Table issueTable;

    public IssueTableLayout(final IssueOverviewScreen screen, final boolean componentsReadOnlyInit) {
        super(screen, componentsReadOnlyInit);
        setSizeFull();
        addSaveButtonClickListener(new StandardClickListener());
        addCancelButtonClickListener(new StandardClickListener());
    }

    @Override
    protected AbstractOrderedLayout buildSaveableForm() {
        return null;
    }

    @Override
    protected AbstractOrderedLayout buildUnsaveableForm() {
        final VerticalLayout componentsLayout = new VerticalLayout();

        issueTable = new Table();
        issueTable.setColumnCollapsingAllowed(true);
        issueTable.setSizeFull();
        issueTable.setImmediate(true);
        issueTable.setSelectable(true);
        issueTable.setColumnReorderingAllowed(true);
        issueTable.setColumnCollapsingAllowed(true);
        issueTable.addItemClickListener(new TableItemClickListener());

        final String[] headerNames = new String[] {CAPTION, STATUS, PRIORITY, TYPE, REPORTER, ASSIGNEE,
                DUE_DATE_PLANNED, DUE_DATE_RESOLVED, DUE_DATE_CLOSED, VERSION,STORYPOINTS, SUMMARY};

        int i = 0;
        for (final String name : headerNames) {
            issueTable.addContainerProperty(name, String.class, "");
            if (i < 4)
                issueTable.setColumnCollapsed(name, false);
            else
                issueTable.setColumnCollapsed(name, true);
            i++;
        }
        issueTable.setItemCaptionPropertyId(CAPTION);
        componentsLayout.addComponent(issueTable);

        return componentsLayout;
    }

    public void setPropertiesFromIssueList(final List<IssueBase> issues) {
        issueTable.removeAllItems();
        for (final IssueBase issue : issues) {
            issueTable.addItem(new Object[] {issue.getText(),
                    issue.getStatus() == null ? "" : issue.getStatus().getStatusName(),
                    issue.getPriority() == null ? "" : issue.getPriority().getPriorityName(),
                    issue.getType() == null ? "" : issue.getType().getTypeName(),
                    issue.getReporter() == null ? "" : issue.getReporter().getLogin(),
                    issue.getAssignee() == null ? "" : issue.getAssignee().getLogin(),
                    issue.getDueDate_planned() == null ? "" : issue.getDueDate_planned().toString(),
                    issue.getDueDate_resolved() == null ? "" : issue.getDueDate_resolved().toString(),
                    issue.getDueDate_closed() == null ? "" : issue.getDueDate_closed().toString(),
                    issue.getVersion() == null ? "" : issue.getVersion().getVersionName(),
                    issue.getStoryPoints() == null ? "" : issue.getStoryPoints().getStorypoint().toString(),
                    issue.getSummary() == null ? "" : issue.getSummary()},
                    issue);
        }
    }

    private class TableItemClickListener implements ItemClickEvent.ItemClickListener {

        @Override
        public void itemClick(ItemClickEvent event) {
            if (event.isDoubleClick()) {
                screen.getIssueTreeLayout().setSelectedItemByIssue((IssueBase) event.getItemId());
            }
        }
    }

    private class StandardClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            setLayoutReadOnly(true);
        }
    }
}
