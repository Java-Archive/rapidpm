package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model;

import org.neo4j.graphdb.Direction;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 06.11.12
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class RelationsDataContainer extends AbstractIssueDataContainer {
    private final static String DIRECTION = "direction";
    private final static String NAME = "name";
    private final static String ISSUEID = "issueid";
    private final static String ISSUE = "issue";
    private final static String RELATION = "relation";

    private final List<RelationItem> createList = new ArrayList<>();
    private final List<RelationItem> deleteList = new ArrayList<>();


    public RelationsDataContainer(final IssueOverviewScreen screen) {
        super(screen);
        this.addContainerProperty(DIRECTION, Direction.class, null);
        this.addContainerProperty(NAME, String.class, null);
        this.addContainerProperty(ISSUEID, String.class, null);
        this.addContainerProperty(ISSUE, IssueBase.class, null);
        this.addContainerProperty(RELATION, IssueRelation.class, null);
    }

    @Override
    protected List<Object> setVisibleColumns() {
        final List<Object> visibleColumns = new ArrayList<>();
        visibleColumns.add(DIRECTION);
        visibleColumns.add(NAME);
        visibleColumns.add(ISSUEID);
        return visibleColumns;
    }

    @Override
    public void fillContainer(final IssueBase issue) {
        setCurrentIssue(issue);
        resetTransactions();
        this.removeAllItems();

        for (final IssueRelation relation : DaoFactorySingelton.getInstance().getIssueRelationDAO().loadAllEntities()) {
            for (final IssueBase connIssue : issue.getConnectedIssues(relation, Direction.OUTGOING)) {
                final Object itemId = this.addItem();
                this.getContainerProperty(itemId,DIRECTION).setValue(Direction.OUTGOING);
                this.getContainerProperty(itemId, NAME).setValue(relation.getOutgoingName());
                this.getContainerProperty(itemId, ISSUEID).setValue(connIssue.getText());
                this.getContainerProperty(itemId,ISSUE).setValue(connIssue);
                this.getContainerProperty(itemId,RELATION).setValue(relation);
            }

            for (final IssueBase connIssue : issue.getConnectedIssues(relation, Direction.INCOMING)) {
                final Object itemId = this.addItem();
                this.getContainerProperty(itemId,DIRECTION).setValue(Direction.INCOMING);
                this.getContainerProperty(itemId, NAME).setValue(relation.getIncomingName());
                this.getContainerProperty(itemId, ISSUEID).setValue(connIssue.getText());
                this.getContainerProperty(itemId,ISSUE).setValue(connIssue);
                this.getContainerProperty(itemId,RELATION).setValue(relation);
            }
        }
    }

    public void addRelation(final IssueBase connIssue, final IssueRelation relation){
        if (connIssue == null)
            throw new NullPointerException("Issue to connect to is null");
        if (relation == null)
            throw new NullPointerException("Relation to connect ist null");

        final Object itemId = this.addItem();
        this.getContainerProperty(itemId, DIRECTION).setValue(Direction.OUTGOING);
        this.getContainerProperty(itemId, NAME).setValue(relation.getOutgoingName());
        this.getContainerProperty(itemId, ISSUEID).setValue(connIssue.getText());
        this.getContainerProperty(itemId, ISSUE).setValue(connIssue);
        this.getContainerProperty(itemId, RELATION).setValue(relation);

        final RelationItem item = new RelationItem();
        item.setConnIssue(connIssue);
        item.setRelation(relation);
        item.setDirection(Direction.OUTGOING);
        createList.add(item);
    }

    public IssueBase getConnIssueFromItemId(final Object itemId) {
        if (itemId == null)
            throw new NullPointerException("ItemId must not be null");
        return (IssueBase)this.getContainerProperty(itemId, ISSUE).getValue();
    }

    @Override
    public boolean removeItem(final Object itemId) {
        if (itemId == null)
            throw new NullPointerException("ItemId to remove is null. Cant delete item.");

        boolean success = false;
        final RelationItem item = new RelationItem();
        item.setConnIssue(getConnIssueFromItemId(itemId));
        item.setRelation((IssueRelation) this.getContainerProperty(itemId, RELATION).getValue());
        item.setDirection((Direction) this.getContainerProperty(itemId, DIRECTION).getValue());
        //if (getCurrentIssue().removeConnectionToIssue(connIssue, relation, direction))
            if (super.removeItem(itemId)) {
                deleteList.add(item);
                success = true;
            }

        return success;
    }

    public List<RelationItem> getDeleteList() {
        return deleteList;
    }

    public List<RelationItem> getCreateList() {
        return createList;
    }

    public void resetTransactions() {
        deleteList.clear();
        createList.clear();
    }
}
