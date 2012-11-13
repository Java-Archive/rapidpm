package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.model;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright by Sven Ruppert // chef@sven-ruppert.de
 * <p/>
 * User: svenruppert
 * Date: 18.10.12
 * Time: 13:58
 * <p/>
 * Version:
 */
public class TimeSheetReport implements Report{
    private static final SimpleDateFormat yyyyMMddFormat = new SimpleDateFormat("yyyy.MM.dd");
    private List<UserWorkLog> userWorkLogs = new ArrayList<>();

    public List<UserWorkLog> getUserWorkLogs() {
        return userWorkLogs;
    }

    public Map<IssueDayUserKey, Long> getIssueDayUserKey2TimeMap() {
        return issueDayUserKey2TimeMap;
    }

    public static class IssueDayUserKey implements Comparable<IssueDayUserKey>{
        private String issuekey;
        private String day;
        private String author;
        private String created;
        private String updated;
        private String assignee;
        private String comment;
        private String summary;
        private String timeSpentString;
        private String timeSpentInMins;

        private IssueDayUserKey(final String issuekey, final String created, String author, final String assignee, String comment, String updated, String summary, String timeSpentString, String timeSpentInMins) {
            this.issuekey = issuekey;
            this.created = created;
            this.author = author;
            this.assignee = assignee;
            this.comment = comment;
            this.updated = updated;
            this.summary = summary;
            this.timeSpentString = timeSpentString;
            this.timeSpentInMins = timeSpentInMins;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof IssueDayUserKey)) return false;

            IssueDayUserKey that = (IssueDayUserKey) o;

            if (!day.equals(that.day)) return false;
            if (!issuekey.equals(that.issuekey)) return false;
            if (!author.equals(that.author)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = issuekey.hashCode();
            result = 31 * result + day.hashCode();
            result = 31 * result + author.hashCode();
            return result;
        }

        public String getIssuekey() {
            return issuekey;
        }

        public void setIssuekey(String issuekey) {
            this.issuekey = issuekey;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
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

        public String getTimeSpentInMins() {
            return timeSpentInMins;
        }

        public void setTimeSpentInMins(String timeSpentInMins) {
            this.timeSpentInMins = timeSpentInMins;
        }

        @Override
        public int compareTo(IssueDayUserKey o) {
            final String s1 = o.getDay() + o.getIssuekey() + o.getAuthor();
            final String s0 = this.getDay() + this.getIssuekey() + o.getAuthor();
            return s0.compareTo(s1);
        }

        @Override
        public String toString() {
            return "IssueDayUserKey{" +
                    "issuekey='" + issuekey + '\'' +
                    ", day='" + day + '\'' +
                    ", author='" + author + '\'' +
                    ", created='" + created + '\'' +
                    ", updated='" + updated + '\'' +
                    ", assignee='" + assignee + '\'' +
                    ", comment='" + comment + '\'' +
                    ", summary='" + summary + '\'' +
                    '}';
        }
    }
    private Map<IssueDayUserKey, Long> issueDayUserKey2TimeMap = new TreeMap<>();


    @Override
    public void execute() {
        //zusammenfassen auf einen Eintrag pro tag und User / Issue
        for (final UserWorkLog userWorkLog : userWorkLogs) {
            final String issueKey = userWorkLog.getIssueKey();
            final Date created = userWorkLog.getCreated();
            final String author = userWorkLog.getAuthor();
            final String assignee = userWorkLog.getAssignee();
            final String comment = userWorkLog.getComment();
            final Date updated = userWorkLog.getUpdated();
            final String summary = userWorkLog.getSummary();
            final String timeSpentString = userWorkLog.getTimeSpentString();
            final String timeSpentInMins = userWorkLog.getTimeSpentInMins();

            final IssueDayUserKey key = new IssueDayUserKey(issueKey, yyyyMMddFormat.format(created), author, assignee, comment, yyyyMMddFormat.format(updated), summary, timeSpentString, timeSpentInMins);
            if (issueDayUserKey2TimeMap.containsKey(key)) {
                //
            } else {
                issueDayUserKey2TimeMap.put(key,0L);
            }
            //final Long reportedTime = issueDayUserKey2TimeMap.get(key) + userWorkLog.getTimeInSeconds();
            //issueDayUserKey2TimeMap.put(key,reportedTime);
        }
    }
}
