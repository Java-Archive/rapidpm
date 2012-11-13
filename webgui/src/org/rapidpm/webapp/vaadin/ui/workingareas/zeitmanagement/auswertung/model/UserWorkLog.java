package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.model;

import java.util.Date;

/**
* Copyright by Sven Ruppert // chef@sven-ruppert.de
* <p/>
* User: svenruppert
* Date: 18.10.12
* Time: 14:17
* <p/>
* Version:
*/
public class UserWorkLog {

    public static final String ASSIGNEE = "assignee";
    public static final String CREATED = "created";
    public static final String UPDATED = "updated";
    public static final String ISSUEKEY = "issueKey";
    public static final String SUMMARY = "summary";
    public static final String AUTHOR = "author";
    public static final String COMMENT = "comment";
    public static final String TIMESPENTSTRING = "timeSpentString";
    public static final String TIMESPENTINMINS = "timeSpentInMins";

    private String assignee;
    private Date created;
    private Date updated;
    private String issueKey;
    private String summary;
    private String timeSpentString;
    private String author;
    private String comment;
    private String timeSpentInMins;

    @Override
    public String toString() {
        return "UserWorkLog{" +
                "assignee='" + assignee + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", issueKey='" + issueKey + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserWorkLog)) return false;

        UserWorkLog that = (UserWorkLog) o;

        if (assignee != null ? !assignee.equals(that.assignee) : that.assignee != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (issueKey != null ? !issueKey.equals(that.issueKey) : that.issueKey != null) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = assignee != null ? assignee.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (issueKey != null ? issueKey.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        return result;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTimeSpentString() {
        return timeSpentString;
    }

    public void setTimeSpentString(String timeSpentString) {
        this.timeSpentString = timeSpentString;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public String getTimeSpentInMins() {
        return timeSpentInMins;
    }

    public void setTimeSpentInMins(String timeSpentInMins) {
        this.timeSpentInMins = timeSpentInMins;
    }
}
