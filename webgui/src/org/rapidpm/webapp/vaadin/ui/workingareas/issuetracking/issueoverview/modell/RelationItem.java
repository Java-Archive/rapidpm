package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import org.neo4j.graphdb.Direction;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 14.11.12
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class RelationItem {

    private IssueBase connIssue;
    private IssueRelation relation;
    private Direction direction;

    public RelationItem() {
        //empty on purpose
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public IssueRelation getRelation() {
        return relation;
    }

    public void setRelation(IssueRelation relation) {
        this.relation = relation;
    }

    public IssueBase getConnIssue() {
        return connIssue;
    }

    public void setConnIssue(IssueBase connIssue) {
        this.connIssue = connIssue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelationItem that = (RelationItem) o;

        if (connIssue != null ? !connIssue.equals(that.connIssue) : that.connIssue != null) return false;
        if (direction != that.direction) return false;
        if (relation != null ? !relation.equals(that.relation) : that.relation != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = connIssue != null ? connIssue.hashCode() : 0;
        result = 31 * result + (relation != null ? relation.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RelationItem{" +
                "connIssue=" + connIssue +
                ", relation=" + relation +
                ", direction=" + direction +
                '}';
    }
}
