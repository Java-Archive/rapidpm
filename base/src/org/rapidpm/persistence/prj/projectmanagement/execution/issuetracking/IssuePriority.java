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
 *        Time: 12:19:00
 */

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class IssuePriority {
    //    private String name;

    public IssuePriority() {
    }
    public IssuePriority(final int prio, final String priorityName) {
        this.prio = prio;
        this.priorityName = priorityName;
    }


    @Id
    @TableGenerator(name = "PKGenIssuePriority", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "IssuePriority_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenIssuePriority")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    private int prio;

    @Basic
    private String priorityName;

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String name) {
        this.priorityName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssuePriority that = (IssuePriority) o;

        if (prio != that.prio) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (priorityName != null ? !priorityName.equals(that.priorityName) : that.priorityName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + prio;
        result = 31 * result + (priorityName != null ? priorityName.hashCode() : 0);
        return result;
    }
}
