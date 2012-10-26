package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.system.security.Benutzer;

import javax.persistence.*;
import java.util.Date;

/**
 * RapidPM - www.rapidpm.org
 * User: sven.ruppert
 * Date: 06.03.11
 * Time: 22:10
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
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
    private String text;

    @OneToOne
    private Benutzer creator;

    @Basic
    private Date created;

    public IssueComment() {
        //empty on purpose
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

    public void setId(final Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String txt) {
        this.text = txt;
    }


    public Benutzer getCreator() {
        return creator;
    }

    public void setCreator(final Benutzer creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueComment that = (IssueComment) o;

        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueComment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", creator=" + creator +
                ", created=" + created +
                '}';
    }
}
