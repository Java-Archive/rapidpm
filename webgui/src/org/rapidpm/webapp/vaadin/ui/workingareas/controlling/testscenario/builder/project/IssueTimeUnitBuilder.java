package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;
import org.rapidpm.persistence.system.security.Benutzer;

import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 21.11.12
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public class IssueTimeUnitBuilder {

    private Date datum;
    private int minutes;
    private Benutzer worker;

    public IssueTimeUnitBuilder setDatum(final Date datum) {
        this.datum = datum;
        return this;
    }

    public IssueTimeUnitBuilder setMinutes(final int minutes) {
        this.minutes = minutes;
        return this;
    }

    public IssueTimeUnitBuilder setWorker(final Benutzer worker) {
        this.worker = worker;
        return this;
    }

    public IssueTimeUnit getIssueTimeUnit(){

        if(datum == null
                || minutes <= 0
                || worker == null)
            throw new IllegalStateException("IssueTimeUnitBuilder: Nicht alle Daten angegeben.");
        
            
        final IssueTimeUnit issueTimeUnit = new IssueTimeUnit();
        issueTimeUnit.setDate(datum);
        issueTimeUnit.setMinutes(minutes);
        issueTimeUnit.setWorker(worker);

        return issueTimeUnit;
    }
}
