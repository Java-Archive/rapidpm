package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.neo4j.graphdb.RelationshipType;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Identifier;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.PersistInGraph;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
public class IssueRelation implements RelationshipType, PersistInGraph {

    @Identifier
    private Long id;

    @Simple
    private String relationName;

    @Simple
    private String outgoingName;

    @Simple
    private String incomingName;

    public IssueRelation() {
        //empty on purpose
    }

    public IssueRelation(final String relationName) {
        this.relationName = relationName;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(final String relationName) {
        this.relationName = relationName;
    }

    public String getOutgoingName() {
        return outgoingName;
    }

    public void setOutgoingName(String outgoingName) {
        this.outgoingName = outgoingName;
    }

    public String getIncomingName() {
        return incomingName;
    }

    public void setIncomingName(String incomingName) {
        this.incomingName = incomingName;
    }

    @Override
    public String toString() {
        return "IssueRelation{" +
                "id=" + id +
                ", relationName='" + relationName + '\'' +
                ", outgoingName='" + outgoingName + '\'' +
                ", incomingName='" + incomingName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueRelation that = (IssueRelation) o;

        if (incomingName != null ? !incomingName.equals(that.incomingName) : that.incomingName != null) return false;
        if (outgoingName != null ? !outgoingName.equals(that.outgoingName) : that.outgoingName != null) return false;
        if (relationName != null ? !relationName.equals(that.relationName) : that.relationName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = relationName != null ? relationName.hashCode() : 0;
        result = 31 * result + (outgoingName != null ? outgoingName.hashCode() : 0);
        result = 31 * result + (incomingName != null ? incomingName.hashCode() : 0);
        return result;
    }

    @Override
    public String name() {
        return getRelationName();
    }
}
