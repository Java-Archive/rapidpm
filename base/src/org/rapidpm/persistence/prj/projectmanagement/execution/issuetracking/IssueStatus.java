package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import javax.persistence.*;

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


//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class IssueStatus {

    public static final String NAME = "statusName";
    public static final String ID = "id";

    @Id
    @TableGenerator(name = "PKGenIssueStatus", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "IssueStatus_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenIssueStatus")
    private Long id;

    @Basic
    private String statusName;

    @Basic
    private String statusFileName;

    public IssueStatus() {
    }

    public IssueStatus(long id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public IssueStatus(final String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String name) {
        this.statusName = name;
    }

    public String getStatusFileName() {
        return statusFileName;
    }

    public void setStatusFileName(String statusFileName) {
        this.statusFileName = statusFileName;
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
