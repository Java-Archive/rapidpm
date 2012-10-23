package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.windows.KontaktWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueadd.IssueAddWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.10.12
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */
public class AddButtonClickListener implements Button.ClickListener {

    private final Tree issueTree;
    private final IssueOverviewScreen screen;

    public AddButtonClickListener(final IssueOverviewScreen screen, final Tree issueTree) {
        this.screen = screen;
        this.issueTree = issueTree;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        //UI.getCurrent().addWindow(new IssueAddWindow(screen, issueTree));
        test();
    }

    public void test() {
        IssueBase issue = GraphDaoFactory.getIssueBaseDAO().loadAllEntities().get(0);
        IssueComment comment1 = new IssueComment();
        comment1.setText("comment1");
        comment1.setCreated(new Date());
        comment1.setCreator(issue.getAssignee());

        IssueComment comment2 = new IssueComment();
        comment2.setText("comment2");
        comment2.setCreated(new Date());
        comment2.setCreator(issue.getAssignee());

        issue.addComment(comment1);
        issue.addComment(comment2);

        System.out.println(issue.toString());
        issue = GraphDaoFactory.getIssueBaseDAO().persist(issue);

    }
}
