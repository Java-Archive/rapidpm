package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import com.vaadin.data.Item;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTestCase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 08.11.12
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
public class TestCasesDataContainer extends AbstractIssueDataContainer {


    private final static String TEXT = "text";

    public TestCasesDataContainer(final IssueOverviewScreen screen) {
        super(screen);
        this.addContainerProperty(TEXT, String.class, null);
    }

    @Override
    protected List<Object> setVisibleColumns() {
        List<Object> visibleColumns = new ArrayList<>();
        visibleColumns.add(TEXT);
        return visibleColumns;
    }

    @Override
    public void fillContainer(IssueBase issue) {
        setCurrentIssue(issue);
        this.removeAllItems();
        for (IssueTestCase testCase : issue.getTestcases()) {
            addTestcase(testCase);
        }
    }

    public boolean addTestcase(IssueTestCase testCase) {
        boolean success = false;
        if (testCase != null) {
            Item itemId = this.addItem(testCase);
            itemId.getItemProperty(TEXT).setValue(testCase.getText());
            success = true;
        }
        return success;
    }
}
