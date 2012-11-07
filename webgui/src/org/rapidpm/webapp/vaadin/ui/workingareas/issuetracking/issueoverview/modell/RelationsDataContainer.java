package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import org.neo4j.graphdb.Direction;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin
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

    private IssueBase currentIssue;

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
        List<Object> visibleColumns = new ArrayList<>();
        visibleColumns.add(DIRECTION);
        visibleColumns.add(NAME);
        visibleColumns.add(ISSUEID);
        return visibleColumns;
    }

    @Override
    public void fillContainer(IssueBase issue) {
        finishedLoading = false;
        currentIssue = issue;
        this.removeAllItems();
        Object itemId;
        for (IssueRelation relation : GraphDaoFactory.getIssueRelationDAO().loadAllEntities()) {
            for (IssueBase connIssue : issue.getConnectedIssues(relation, Direction.OUTGOING)) {
                itemId = this.addItem();
                this.getContainerProperty(itemId,DIRECTION).setValue(Direction.OUTGOING);
                this.getContainerProperty(itemId, NAME).setValue(relation.getOutgoingName());
                this.getContainerProperty(itemId, ISSUEID).setValue(connIssue.getText());
                this.getContainerProperty(itemId,ISSUE).setValue(connIssue);
                this.getContainerProperty(itemId,RELATION).setValue(relation);
            }

            for (IssueBase connIssue : issue.getConnectedIssues(relation, Direction.INCOMING)) {
                itemId = this.addItem();
                this.getContainerProperty(itemId,DIRECTION).setValue(Direction.INCOMING);
                this.getContainerProperty(itemId, NAME).setValue(relation.getIncomingName());
                this.getContainerProperty(itemId, ISSUEID).setValue(connIssue.getText());
                this.getContainerProperty(itemId,ISSUE).setValue(connIssue);
                this.getContainerProperty(itemId,RELATION).setValue(relation);
            }
        }
        finishedLoading = true;
    }

    @Override
    public IssueBase getConnIssueFromItemId(Object itemId) {
        return (IssueBase)this.getContainerProperty(itemId, ISSUE).getValue();
    }

    @Override
    public boolean refresh() {
        boolean success = false;
        if (currentIssue != null) {
            fillContainer(currentIssue);
            success = true;
        } else
            logger.error("CurrentIssue is null. Cant delete item.");

        return success;
    }

    @Override
    public boolean removeItem(Object itemId) {
        boolean success = false;
        if (currentIssue != null) {
            IssueBase connIssue = getConnIssueFromItemId(itemId);
            IssueRelation relation = (IssueRelation) this.getContainerProperty(itemId, RELATION).getValue();
            Direction direction = (Direction) this.getContainerProperty(itemId, DIRECTION).getValue();
            if (currentIssue.removeConnectionToIssue(connIssue, relation, direction))
                if (super.removeItem(itemId))
                    success = true;
        } else {
            logger.error("CurrentIssue is null. Cant delete item.");
        }

        return success;
    }


}
