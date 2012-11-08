package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 08.11.12
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
public class TestCasesDataContainer extends AbstractIssueDataContainer  {


    public TestCasesDataContainer(final IssueOverviewScreen screen) {
        super(screen);
    }

    @Override
    protected List<Object> setVisibleColumns() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void fillContainer(IssueBase issue) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
