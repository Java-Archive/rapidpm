package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project.IssueBaseBuilder;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.stab.BenutzerDemoDaten;

import java.sql.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class IssueBaseDemoDaten {

    private final static IssueStatus plannedIssueStatus;
    private final static IssueStatus closedIssueStatus;
    private final static IssueStatus resolvedIssueStatus;
    static {
        plannedIssueStatus = new IssueStatus();
        plannedIssueStatus.setStatusName("Planned");

        closedIssueStatus = new IssueStatus();
        closedIssueStatus.setStatusName("Closed");

        resolvedIssueStatus = new IssueStatus();
        resolvedIssueStatus.setStatusName("Resolved");
    }

    private List<IssueBase> planningUnit1_issueBaseList;
    private List<IssueBase> planningUnit1_1_issueBaseList;
    private List<IssueBase> planningUnit1_2_issueBaseList;
    private List<IssueBase> planningUnit2_issueBaseList;

    private final BenutzerDemoDaten benutzerDemoDaten = new BenutzerDemoDaten();
    private final IssueTimeUnitDemoDatata issueTimeUnitDemoDatata = new IssueTimeUnitDemoDatata();

    public IssueBaseDemoDaten(){
        final IssueBase rpm_1_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 1")
                .setDueDateClosed(Date.valueOf("01.10.2012"))
                .setDueDatePlanned(Date.valueOf("01.10.2012"))
                .setDueDateResolved(Date.valueOf("01.10.2012"))
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(closedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitDemoDatata.getRpm1_timeUnitsUsedList())
                .getIssueBase();
        planningUnit1_issueBaseList.add(rpm_1_issue);

        final IssueBase rpm_2_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 2")
                .setDueDateClosed(Date.valueOf("03.10.2012"))
                .setDueDatePlanned(Date.valueOf("05.10.2012"))
                .setDueDateResolved(Date.valueOf("06.10.2012"))
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(closedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitDemoDatata.getRpm2_timeUnitsUsedList())
                .getIssueBase();
        planningUnit1_1_issueBaseList.add(rpm_2_issue);

        final IssueBase rpm_3_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 3")
                .setDueDateClosed(Date.valueOf("06.10.2012"))
                .setDueDatePlanned(Date.valueOf("06.10.2012"))
                .setDueDateResolved(Date.valueOf("07.10.2012"))
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(closedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitDemoDatata.getRpm3_timeUnitsUsedList())
                .getIssueBase();
        planningUnit1_1_issueBaseList.add(rpm_3_issue);

        final IssueBase rpm_4_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 4")
                .setDueDateClosed(Date.valueOf("06.10.2012"))
                .setDueDatePlanned(Date.valueOf("07.10.2012"))
                .setDueDateResolved(Date.valueOf("07.10.2012"))
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(closedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitDemoDatata.getRpm4_timeUnitsUsedList())
                .getIssueBase();
        planningUnit1_1_issueBaseList.add(rpm_4_issue);

        final IssueBase rpm_5_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 5")
                .setDueDateClosed(Date.valueOf("07.10.2012"))
                .setDueDatePlanned(Date.valueOf("07.10.2012"))
                .setDueDateResolved(Date.valueOf("08.10.2012"))
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(closedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitDemoDatata.getRpm5_timeUnitsUsedList())
                .getIssueBase();
        planningUnit1_1_issueBaseList.add(rpm_5_issue);

        final IssueBase rpm_6_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 6")
                .setDueDateClosed(Date.valueOf("07.10.2012"))
                .setDueDatePlanned(Date.valueOf("08.10.2012"))
                .setDueDateResolved(null)
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(resolvedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitDemoDatata.getRpm6_timeUnitsUsedList())
                .getIssueBase();
        planningUnit1_2_issueBaseList.add(rpm_6_issue);

        final IssueBase rpm_7_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 7")
                .setDueDateClosed(Date.valueOf("10.10.2012"))
                .setDueDatePlanned(Date.valueOf("10.10.2012"))
                .setDueDateResolved(Date.valueOf("11.10.2012"))
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(resolvedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitDemoDatata.getRpm7_timeUnitsUsedList())
                .getIssueBase();
        planningUnit2_issueBaseList.add(rpm_7_issue);
    }

    public List<IssueBase> getPlanningUnit1_issueBaseList() {
        return planningUnit1_issueBaseList;
    }

    public List<IssueBase> getPlanningUnit1_1_issueBaseList() {
        return planningUnit1_1_issueBaseList;
    }

    public List<IssueBase> getPlanningUnit1_2_issueBaseList() {
        return planningUnit1_2_issueBaseList;
    }

    public List<IssueBase> getPlanningUnit2_issueBaseList() {
        return planningUnit2_issueBaseList;
    }
}
