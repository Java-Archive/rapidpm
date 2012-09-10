package org.rapidpm.persistence.prj.bewegungsdaten.msgcenter.msg;

import org.rapidpm.persistence.system.security.Benutzer;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.Date;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 20.09.2010
 *        Time: 20:24:11
 */

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class PersonalMessage {
    private static final Logger logger = Logger.getLogger(PersonalMessage.class);

    @Id
    @TableGenerator(name = "PKGenPersonalMessage", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "PersonalMessage_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPersonalMessage")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Basic
    private boolean unread;
    @Basic
    private Date created;
    @OneToOne(cascade = CascadeType.REFRESH)
    private Benutzer sender;
    @OneToOne(cascade = CascadeType.REFRESH)
    private Benutzer empfaenger;

    @Basic
    private String subject;
    @Basic
    private String text;
    //    private Long id;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalMessage)) {
            return false;
        }

        final PersonalMessage that = (PersonalMessage) o;

        if (!empfaenger.equals(that.empfaenger)) {
            return false;
        }
        if (!sender.equals(that.sender)) {
            return false;
        }
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) {
            return false;
        }
        if (!text.equals(that.text)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = sender.hashCode();
        result = 31 * result + empfaenger.hashCode();
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + text.hashCode();
        return result;
    }

    public Date getCreated() {
        return created;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(final boolean unread) {
        this.unread = unread;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Benutzer getSender() {
        return sender;
    }

    public void setSender(final Benutzer sender) {
        this.sender = sender;
    }

    public Benutzer getEmpfaenger() {
        return empfaenger;
    }

    public void setEmpfaenger(final Benutzer empfaenger) {
        this.empfaenger = empfaenger;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PersonalMessage");
        sb.append("{unread=").append(unread);
        sb.append(", created=").append(created);
        sb.append(", sender=").append(sender);
        sb.append(", empfaenger=").append(empfaenger);
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", id=").append(getId());
        sb.append('}');
        return sb.toString();
    }

    //    @Override
    //    public Long getId() {
    //        return id;
    //    }
    //
    //    @Override
    //    public void setId(final Long id) {
    //        this.id = id;
    //    }
}
