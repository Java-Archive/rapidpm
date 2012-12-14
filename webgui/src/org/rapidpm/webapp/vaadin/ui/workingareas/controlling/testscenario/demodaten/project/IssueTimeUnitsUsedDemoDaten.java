package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project.IssueTimeUnitBuilder;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.stab.BenutzerDemoDaten;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 21.11.12
 * Time: 18:49
 * To change this template use File | Settings | File Templates.
 */
public class IssueTimeUnitsUsedDemoDaten {

    private List<IssueTimeUnit> rpm1_timeUnitsUsedList = new ArrayList<>();
    private List<IssueTimeUnit> rpm2_timeUnitsUsedList = new ArrayList<>();
    private List<IssueTimeUnit> rpm3_timeUnitsUsedList = new ArrayList<>();
    private List<IssueTimeUnit> rpm4_timeUnitsUsedList = new ArrayList<>();
    private List<IssueTimeUnit> rpm5_timeUnitsUsedList = new ArrayList<>();
    private List<IssueTimeUnit> rpm6_timeUnitsUsedList = new ArrayList<>();
    private List<IssueTimeUnit> rpm7_timeUnitsUsedList = new ArrayList<>();

    private final BenutzerDemoDaten benutzerDemoDaten = new BenutzerDemoDaten();

    public IssueTimeUnitsUsedDemoDaten(){
        final IssueTimeUnit rpm1_1_TimeUnit = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("2012-10-2"))
                .setMinutes(120)
                .setWorker(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .getIssueTimeUnit();
        rpm1_timeUnitsUsedList.add(rpm1_1_TimeUnit);

        final IssueTimeUnit rpm2_1_TimeUnit = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("2012-10-3"))
                .setMinutes(360)
                .setWorker(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .getIssueTimeUnit();
        rpm2_timeUnitsUsedList.add(rpm2_1_TimeUnit);

        final IssueTimeUnit rpm2_2_TimeUnit = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("2012-10-4"))
                .setMinutes(180)
                .setWorker(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .getIssueTimeUnit();
        rpm2_timeUnitsUsedList.add(rpm2_2_TimeUnit);

        final IssueTimeUnit rpm2_3_TimeUnit = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("2012-10-5"))
                .setMinutes(180)
                .setWorker(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .getIssueTimeUnit();
        rpm2_timeUnitsUsedList.add(rpm2_3_TimeUnit);

        final IssueTimeUnit rpm2_4_TimeUnit = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("2012-10-6"))
                .setMinutes(240)
                .setWorker(benutzerDemoDaten.getPeterMuellerBenutzer())
                .getIssueTimeUnit();
        rpm2_timeUnitsUsedList.add(rpm2_4_TimeUnit);


        final IssueTimeUnit rpm3_1_TimeUnit = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("2012-10-6"))
                .setMinutes(300)
                .setWorker(benutzerDemoDaten.getPeterMuellerBenutzer())
                .getIssueTimeUnit();
        rpm2_timeUnitsUsedList.add(rpm3_1_TimeUnit);

        final IssueTimeUnit rpm4_1_TimeUnit = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("2012-10-6"))
                .setMinutes(540)
                .setWorker(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .getIssueTimeUnit();
        rpm2_timeUnitsUsedList.add(rpm4_1_TimeUnit);

        final IssueTimeUnit rpm5_1_TimeUnit = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("2012-10-7"))
                .setMinutes(180)
                .setWorker(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .getIssueTimeUnit();
        rpm2_timeUnitsUsedList.add(rpm5_1_TimeUnit);

        final IssueTimeUnit rpm5_2_TimeUnit = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("2012-10-7"))
                .setMinutes(180)
                .setWorker(benutzerDemoDaten.getPeterMuellerBenutzer())
                .getIssueTimeUnit();
        rpm2_timeUnitsUsedList.add(rpm5_2_TimeUnit);

        final IssueTimeUnit rpm6_1_TimeUnit = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("2012-10-7"))
                .setMinutes(120)
                .setWorker(benutzerDemoDaten.getKarlSchmidtBenutzer())
                .getIssueTimeUnit();
        rpm2_timeUnitsUsedList.add(rpm6_1_TimeUnit);

        final IssueTimeUnit rpm7_1_TimeUnit = new IssueTimeUnitBuilder()
                .setDatum(Date.valueOf("2012-10-8"))
                .setMinutes(330)
                .setWorker(benutzerDemoDaten.getPeterMuellerBenutzer())
                .getIssueTimeUnit();
        rpm2_timeUnitsUsedList.add(rpm7_1_TimeUnit);
    }


    public List<IssueTimeUnit> getRpm1_timeUnitsUsedList() {
        return rpm1_timeUnitsUsedList;
    }

    public List<IssueTimeUnit> getRpm2_timeUnitsUsedList() {
        return rpm2_timeUnitsUsedList;
    }

    public List<IssueTimeUnit> getRpm3_timeUnitsUsedList() {
        return rpm3_timeUnitsUsedList;
    }

    public List<IssueTimeUnit> getRpm4_timeUnitsUsedList() {
        return rpm4_timeUnitsUsedList;
    }

    public List<IssueTimeUnit> getRpm5_timeUnitsUsedList() {
        return rpm5_timeUnitsUsedList;
    }

    public List<IssueTimeUnit> getRpm6_timeUnitsUsedList() {
        return rpm6_timeUnitsUsedList;
    }

    public List<IssueTimeUnit> getRpm7_timeUnitsUsedList() {
        return rpm7_timeUnitsUsedList;
    }
}
