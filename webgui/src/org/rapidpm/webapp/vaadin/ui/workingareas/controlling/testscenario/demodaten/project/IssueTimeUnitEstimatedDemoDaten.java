package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project.IssueTimeUnitBuilder;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.stab.BenutzerDemoDaten;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 29.11.12
 * Time: 12:18
 * To change this template use File | Settings | File Templates.
 */
public class IssueTimeUnitEstimatedDemoDaten {
    private IssueTimeUnit rpm1_timeUnitEstimated;
    private IssueTimeUnit rpm2_timeUnitEstimated;
    private IssueTimeUnit rpm3_timeUnitEstimated;
    private IssueTimeUnit rpm4_timeUnitEstimated;
    private IssueTimeUnit rpm5_timeUnitEstimated;
    private IssueTimeUnit rpm6_timeUnitEstimated;
    private IssueTimeUnit rpm7_timeUnitEstimated;

    private final BenutzerDemoDaten benutzerDemoDaten = new BenutzerDemoDaten();

    public IssueTimeUnitEstimatedDemoDaten(){
        rpm1_timeUnitEstimated = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("02.10.2012"))
                .setMinutes(120)
                .setWorker(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .getIssueTimeUnit();

        rpm2_timeUnitEstimated = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("05.10.2012"))
                .setMinutes(1080)
                .setWorker(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .getIssueTimeUnit();

        rpm3_timeUnitEstimated= new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("06.10.2012"))
                .setMinutes(360)
                .setWorker(benutzerDemoDaten.getPeterMuellerBenutzer())
                .getIssueTimeUnit();

        rpm4_timeUnitEstimated= new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("06.10.2012"))
                .setMinutes(360)
                .setWorker(benutzerDemoDaten.getKarlSchmidtBenutzer())
                .getIssueTimeUnit();

        rpm5_timeUnitEstimated= new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("06.10.2012"))
                .setMinutes(360)
                .setWorker(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .getIssueTimeUnit();

        rpm6_timeUnitEstimated= new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("07.10.2012"))
                .setMinutes(360)
                .setWorker(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .getIssueTimeUnit();

        rpm7_timeUnitEstimated= new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("07.10.2012"))
                .setMinutes(360)
                .setWorker(benutzerDemoDaten.getPeterMuellerBenutzer())
                .getIssueTimeUnit();
    }


    public IssueTimeUnit getRpm1_timeUnitEstimated() {
        return rpm1_timeUnitEstimated;
    }

    public IssueTimeUnit getRpm2_timeUnitEstimated() {
        return rpm2_timeUnitEstimated;
    }

    public IssueTimeUnit getRpm3_timeUnitEstimated() {
        return rpm3_timeUnitEstimated;
    }

    public IssueTimeUnit getRpm4_timeUnitEstimated() {
        return rpm4_timeUnitEstimated;
    }

    public IssueTimeUnit getRpm5_timeUnitEstimated() {
        return rpm5_timeUnitEstimated;
    }

    public IssueTimeUnit getRpm6_timeUnitEstimated() {
        return rpm6_timeUnitEstimated;
    }

    public IssueTimeUnit getRpm7_timeUnitEstimated() {
        return rpm7_timeUnitEstimated;
    }
}
