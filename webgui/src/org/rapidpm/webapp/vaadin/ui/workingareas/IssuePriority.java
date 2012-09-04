package org.rapidpm.webapp.vaadin.ui.workingareas;

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
public class IssuePriority {

    //    private String name;
    private Long id;
    private int prio;
    private String priorityName;

    public IssuePriority() {
    }
    public IssuePriority(final int prio, final String priorityName) {
        this.prio = prio;
        this.priorityName = priorityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

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
}
