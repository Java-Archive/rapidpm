package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import com.vaadin.data.Item;
import org.neo4j.graphdb.Direction;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 07.11.12
 * Time: 09:44
 * To change this template use File | Settings | File Templates.
 */
public class CommentsDataContainer extends AbstractIssueDataContainer {


    private final static String TEXT = "text";
    private final static String CREATOR = "creator";
    private final static String CREATED = "created";
    private final DateFormat dateFormat;

    public CommentsDataContainer(final IssueOverviewScreen screen) {
        super(screen);
        dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        this.addContainerProperty(TEXT, String.class, null);
        this.addContainerProperty(CREATOR, String.class, null);
        this.addContainerProperty(CREATED, String.class, null);
    }

    @Override
    protected List<Object> setVisibleColumns() {
        List<Object> visibleColumns = new ArrayList<>();
        visibleColumns.add(TEXT);
        visibleColumns.add(CREATOR);
        visibleColumns.add(CREATED);
        return visibleColumns;
    }

    @Override
    public void fillContainer(IssueBase issue) {
        setCurrentIssue(issue);
        this.removeAllItems();
        for (IssueComment comment : issue.getComments()) {
            addComment(comment);
        }
    }

    public boolean addComment(IssueComment comment) {
        boolean success = false;
        if (comment != null) {
            Item itemId = this.addItem(comment);
            itemId.getItemProperty(TEXT).setValue(comment.getText());
            itemId.getItemProperty(CREATOR).setValue(comment.getCreator().getLogin());
            itemId.getItemProperty(CREATED).setValue(dateFormat.format(comment.getCreated()));
            success = true;
        }
        return success;
    }
}
