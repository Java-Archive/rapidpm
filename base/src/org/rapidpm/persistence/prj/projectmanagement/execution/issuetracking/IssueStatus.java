package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Identifier;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

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

public class IssueStatus {

    public static final String NAME = "statusName";

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
        return null;
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

        IssueStatus that = (IssueStatus) o;

        if (statusName != null ? !statusName.equals(that.statusName) : that.statusName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return statusName != null ? statusName.hashCode() : 0;
    }
}
