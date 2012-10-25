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
 *        Time: 12:19:00
 */

public class IssuePriority {
    //    private String name;

    public static final String NAME = "priorityName";

    @Identifier
    private Long id;

    @Simple
    private Integer prio;

    @Simple
    private String priorityName;

    @Simple
    private String priorityFileName;

    public IssuePriority() {
        //empty on purpose
    }

    public IssuePriority(final int prio, final String priorityName) {
        this.prio = prio;
        this.priorityName = priorityName;
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

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(final String name) {
        this.priorityName = name;
    }

    public String getPriorityFileName() {
        return priorityFileName;
    }

    public void setPriorityFileName(final String priorityFileName) {
        this.priorityFileName = priorityFileName;
    }

    @Override
    public String toString() {
        return "IssuePriority{" +
                "id=" + id +
                ", prio=" + prio +
                ", priorityName='" + priorityName + '\'' +
                ", priorityFileName='" + priorityFileName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssuePriority that = (IssuePriority) o;

        if (prio != that.prio) return false;
        if (priorityName != null ? !priorityName.equals(that.priorityName) : that.priorityName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = prio;
        result = 31 * result + (priorityName != null ? priorityName.hashCode() : 0);
        return result;
    }
}
