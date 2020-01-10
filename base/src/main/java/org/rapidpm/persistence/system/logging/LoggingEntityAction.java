package org.rapidpm.persistence.system.logging;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 30.05.11
 * Time: 17:28
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */


import javax.persistence.*;

@Entity
public class LoggingEntityAction {

    @TableGenerator(name = "PKGenLoggingEntityAction", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "LoggingEntityAction_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenLoggingEntityAction")
    @Id
    private Long id;

    @Basic
    private String action;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("LoggingEntityAction");
        sb.append("{action='").append(action).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoggingEntityAction)) {
            return false;
        }

        final LoggingEntityAction that = (LoggingEntityAction) o;

        if (action != null ? !action.equals(that.action) : that.action != null) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }

    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}