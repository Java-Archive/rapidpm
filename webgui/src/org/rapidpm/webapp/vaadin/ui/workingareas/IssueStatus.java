package org.rapidpm.webapp.vaadin.ui.workingareas;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;

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
public class IssueStatus {

    private Long id;
    private String statusName;
    private IssueStatusEnum issueStati;

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

    public IssueStatusEnum getIssueStati() {
        return issueStati;
    }

    public void setIssueStati(IssueStatusEnum issueStati) {
        this.issueStati = issueStati;
    }
}
