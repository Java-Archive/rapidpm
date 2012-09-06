package org.rapidpm.webapp.vaadin.ui.workingareas.deprecated;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 12.04.12
 * Time: 13:28
 */
public abstract class Teil {

    protected String name;
    protected Status status;
    protected Priority priority;
    protected Schwierigkeitsgrad schwierigkeitsgrad;
    protected String planer;
    protected Date dateCreated;
    protected Date dateUpdated;
    protected String comment;

    public Teil(final String name) {
        this.name = name;
        status = Status.Open;
        priority = Priority.Major;
        schwierigkeitsgrad = Schwierigkeitsgrad.Normal;
        planer = "Max Mustermann";
        dateCreated = dateUpdated = new Date();
        comment = "Hier steht ein Kommentar";
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(final Priority priority) {
        this.priority = priority;
    }

    public Schwierigkeitsgrad getSchwierigkeitsgrad() {
        return schwierigkeitsgrad;
    }

    public void setSchwierigkeitsgrad(final Schwierigkeitsgrad schwierigkeitsgrad) {
        this.schwierigkeitsgrad = schwierigkeitsgrad;
    }

    public String getPlaner() {
        return planer;
    }

    public void setPlaner(final String planer) {
        this.planer = planer;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(final Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Teil");
        sb.append("{name='").append(name).append('\'');
        sb.append(", status=").append(status);
        sb.append(", priority=").append(priority);
        sb.append(", schwierigkeitsgrad=").append(schwierigkeitsgrad);
        sb.append(", planer='").append(planer).append('\'');
        sb.append(", dateCreated=").append(dateCreated);
        sb.append(", dateUpdated=").append(dateUpdated);
        sb.append(", comment='").append(comment).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static enum Status {
        Open("status_open.gif"),
        InProgress("status_inprogress.gif"),
        Resolved("status_resolved.gif"),
        Closed("status_closed.gif"),
        OnHold("status_information.gif");

        private final Resource icon;

        private Status(final String iconPath) {
            this.icon = new ThemeResource("images/" + iconPath);
        }

        public Resource getIcon() {
            return icon;
        }
    }

    public static enum Priority {
        Blocker("priority_blocker.gif"),
        Critical("priority_critical.gif"),
        Major("priority_major.gif"),
        Trivial("priority_trivial.gif"),
        Minor("priority_minor.gif");

        private final Resource icon;

        private Priority(final String iconPath) {
            this.icon = new ThemeResource("images/" + iconPath);
        }

        public Resource getIcon() {
            return icon;
        }
    }

    public static enum Schwierigkeitsgrad {
        Einfach, Normal, Schwer
    }
}
