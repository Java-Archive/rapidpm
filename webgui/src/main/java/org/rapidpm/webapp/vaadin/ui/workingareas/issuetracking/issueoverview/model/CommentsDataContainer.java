package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model;

import com.vaadin.data.Item;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
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
        final List<Object> visibleColumns = new ArrayList<>();
        visibleColumns.add(TEXT);
        visibleColumns.add(CREATOR);
        visibleColumns.add(CREATED);
        return visibleColumns;
    }

    @Override
    public void fillContainer(final IssueBase issue) {
        setCurrentIssue(issue);
        this.removeAllItems();
        for (final IssueComment comment : issue.getComments()) {
            addComment(comment);
        }
    }

    public void addComment(final IssueComment comment) {
        if (comment == null)
            throw new NullPointerException("Comment to add must not be null");

        final Item itemId = this.addItem(comment);
        itemId.getItemProperty(TEXT).setValue(comment.getText());
        itemId.getItemProperty(CREATOR).setValue(comment.getCreator().getLogin());
        itemId.getItemProperty(CREATED).setValue(dateFormat.format(comment.getCreated()));
    }
}
