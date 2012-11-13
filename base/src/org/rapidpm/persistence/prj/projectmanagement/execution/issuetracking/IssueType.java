package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Identifier;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.PersistInGraph;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 12.10.12
 * Time: 08:22
 * To change this template use File | Settings | File Templates.
 */

public class IssueType  implements PersistInGraph {

    @Identifier
    private Long id;

    @Simple
    private String typeName;

    @Simple
    private String typeFileName;

    public IssueType() {
        //empty on purpose
    }

    public IssueType(final String typeName) {
        this.typeName = typeName;
    }

//    public List<IssueBase> getConnectedIssues() {
//        return GraphDaoFactory.getIssueTypeDAO().getConnectedIssues(this);
//    }

    public List<IssueBase> getConnectedIssuesFromProject(final Long projectId) {
        return GraphDaoFactory.getIssueTypeDAO().getConnectedIssuesFromProject(this, projectId);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    public String getTypeFileName() {
        return typeFileName;
    }

    public void setTypeFileName(final String typeFileName) {
        this.typeFileName = typeFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueType issueType = (IssueType) o;

        if (id != null ? !id.equals(issueType.id) : issueType.id != null) return false;
        if (typeFileName != null ? !typeFileName.equals(issueType.typeFileName) : issueType.typeFileName != null) return false;
        if (typeName != null ? !typeName.equals(issueType.typeName) : issueType.typeName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (typeFileName != null ? typeFileName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", typeFileName='" + typeFileName + '\'' +
                '}';
    }

    @Override
    public String name() {
        return getTypeName();
    }
}
