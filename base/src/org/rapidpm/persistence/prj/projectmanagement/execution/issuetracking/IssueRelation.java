package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.neo4j.graphdb.RelationshipType;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Identifier;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.PersistInGraph;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 18.10.12
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
public class IssueRelation implements RelationshipType, PersistInGraph {

    @Identifier
    private Long id;

    @Simple
    private Long projectid;

    @Simple
    private String relationName;

    @Simple
    private String outgoingName;

    @Simple
    private String incomingName;

    public IssueRelation() {
        //empty on purpose
    }

    public List<IssueBase> getConnectedIssuesFromProject(final Long projectId) {
        return DaoFactorySingelton.getInstance().getIssueRelationDAO().getConnectedIssuesFromProject(this, projectId);
    }

    public Long getId() {
        return id;
    }

    private void setId(final Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectid;
    }

    public void setProjectId(final Long projectid) {
        this.projectid = projectid;
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

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (incomingName != null ? !incomingName.equals(that.incomingName) : that.incomingName != null) return false;
        if (outgoingName != null ? !outgoingName.equals(that.outgoingName) : that.outgoingName != null) return false;
        if (relationName != null ? !relationName.equals(that.relationName) : that.relationName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (relationName != null ? relationName.hashCode() : 0);
        result = 31 * result + (outgoingName != null ? outgoingName.hashCode() : 0);
        result = 31 * result + (incomingName != null ? incomingName.hashCode() : 0);
        return result;
    }

    @Override
    public String name() {
        return getRelationName();
    }
}
