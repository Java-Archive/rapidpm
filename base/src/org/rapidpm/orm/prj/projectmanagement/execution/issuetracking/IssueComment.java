package org.rapidpm.orm.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.orm.system.security.Benutzer;

import javax.persistence.*;
import java.util.Date;

/**
 * RapidPM - www.rapidpm.org
 * User: sven.ruppert
 * Date: 06.03.11
 * Time: 22:10
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Entity
public class IssueComment {


    @TableGenerator(name = "PKGenIssueComment", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "IssueComment_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenIssueComment")
    @Id
    private Long id;

    @Basic
    @Column(columnDefinition = "TEXT")
    private String txt;
    @OneToOne
    private Benutzer creator;
    @Basic
    private Date created;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueComment)) {
            return false;
        }

        final IssueComment that = (IssueComment) o;

        if (created != null ? !created.equals(that.created) : that.created != null) {
            return false;
        }
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (txt != null ? !txt.equals(that.txt) : that.txt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("IssueComment");
        sb.append("{created=").append(created);
        sb.append(", id=").append(id);
        sb.append(", txt='").append(txt).append('\'');
        sb.append(", creator=").append(creator);
        sb.append('}');
        return sb.toString();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }


    public Benutzer getCreator() {
        return creator;
    }

    public void setCreator(Benutzer creator) {
        this.creator = creator;
    }
}