package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Identifier;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.PersistInGraph;

import javax.persistence.*;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 31.08.2010
 *        Time: 12:21:28
 */

public class IssueStatus  implements PersistInGraph {

    @Identifier
    private Long id;

    @Simple
    private String statusName;

    @Simple
    private String statusFileName;

    public IssueStatus() {
        //empty on purpose
    }

    public IssueStatus(final String statusName) {
        this.statusName = statusName;
    }

    public List<IssueBase> getConnectedIssues() {
        return GraphDaoFactory.getIssueStatusDAO().getConnectedIssues(this);
    }

    public List<IssueBase> getConnectedIssuesFromProject(final Long projectId) {
        return GraphDaoFactory.getIssueStatusDAO().getConnectedIssuesFromProject(this, projectId);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(final String name) {
        this.statusName = name;
    }

    public String getStatusFileName() {
        return statusFileName;
    }

    public void setStatusFileName(final String statusFileName) {
        this.statusFileName = statusFileName;
    }

    @Override
    public String toString() {
        return "IssueStatus{" +
                "id=" + id +
                ", statusName='" + statusName + '\'' +
                ", statusFileName='" + statusFileName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueStatus status = (IssueStatus) o;

        if (id != null ? !id.equals(status.id) : status.id != null) return false;
        if (statusFileName != null ? !statusFileName.equals(status.statusFileName) : status.statusFileName != null)
            return false;
        if (statusName != null ? !statusName.equals(status.statusName) : status.statusName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (statusName != null ? statusName.hashCode() : 0);
        result = 31 * result + (statusFileName != null ? statusFileName.hashCode() : 0);
        return result;
    }

    @Override
    public String name() {
        return getStatusName();
    }
}
