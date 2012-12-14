package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project;

import com.google.gwt.dom.builder.shared.IFrameBuilder;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project.IssueBaseBuilder;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.stab.BenutzerDemoDaten;

import java.sql.Date;
import java.util.ArrayList;
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

    private List<IssueBase> planningUnit1_issueBaseList = new ArrayList<>();
    private List<IssueBase> planningUnit1_1_issueBaseList= new ArrayList<>();
    private List<IssueBase> planningUnit1_2_issueBaseList= new ArrayList<>();
    private List<IssueBase> planningUnit2_issueBaseList= new ArrayList<>();

    private IssueBase issueWithSubIssues;
    private IssueBase issueWithoutSubIssues;
    private IssueBase issueWithTimeUnitsEstimatedNull;
    private IssueBase issueWithTimeUnitsUnisUsedNull;
    private IssueBase issueWithEmptyTimeUnitEstimated;
    private IssueBase issueWithEmptyTimeUnitsUsed;

    private final BenutzerDemoDaten benutzerDemoDaten = new BenutzerDemoDaten();
    private final IssueTimeUnitsUsedDemoDaten issueTimeUnitsUsedDemoDaten = new IssueTimeUnitsUsedDemoDaten();
    private final IssueTimeUnitEstimatedDemoDaten issueTimeUnitEstimatedDemoDaten = new IssueTimeUnitEstimatedDemoDaten();

    public IssueBaseDemoDaten(long projectId){
        final IssueBase rpm_1_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 1")
                .setDueDateClosed(Date.valueOf("2012-10-1"))
                .setDueDatePlanned(Date.valueOf("2012-10-1"))
                .setDueDateResolved(Date.valueOf("2012-10-1"))
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(closedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitsUsedDemoDaten.getRpm1_timeUnitsUsedList())
                .setTimeUnitEstimated(issueTimeUnitEstimatedDemoDaten.getRpm1_timeUnitEstimated())
                .getIssueBase();
        if(rpm_1_issue == null)
            System.out.println("WTF???");
        planningUnit1_issueBaseList.add(rpm_1_issue);

        final IssueBase rpm_2_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 2")
                .setDueDateClosed(Date.valueOf("2012-10-3"))
                .setDueDatePlanned(Date.valueOf("2012-10-5"))
                .setDueDateResolved(Date.valueOf("2012-10-6"))
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(closedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitsUsedDemoDaten.getRpm2_timeUnitsUsedList())
                .setTimeUnitEstimated(issueTimeUnitEstimatedDemoDaten.getRpm2_timeUnitEstimated())
                .getIssueBase();
        planningUnit1_1_issueBaseList.add(rpm_2_issue);

        final IssueBase rpm_3_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 3")
                .setDueDateClosed(Date.valueOf("2012-10-6"))
                .setDueDatePlanned(Date.valueOf("2012-10-6"))
                .setDueDateResolved(Date.valueOf("2012-10-7"))
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(closedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitsUsedDemoDaten.getRpm3_timeUnitsUsedList())
                .setTimeUnitEstimated(issueTimeUnitEstimatedDemoDaten.getRpm3_timeUnitEstimated())
                .getIssueBase();
        planningUnit1_1_issueBaseList.add(rpm_3_issue);

        final IssueBase rpm_4_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 4")
                .setDueDateClosed(Date.valueOf("2012-10-6"))
                .setDueDatePlanned(Date.valueOf("2012-10-7"))
                .setDueDateResolved(Date.valueOf("2012-10-7"))
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(closedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitsUsedDemoDaten.getRpm4_timeUnitsUsedList())
                .setTimeUnitEstimated(issueTimeUnitEstimatedDemoDaten.getRpm4_timeUnitEstimated())
                .getIssueBase();
        planningUnit1_1_issueBaseList.add(rpm_4_issue);

        final IssueBase rpm_5_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 5")
                .setDueDateClosed(Date.valueOf("2012-10-7"))
                .setDueDatePlanned(Date.valueOf("2012-10-7"))
                .setDueDateResolved(Date.valueOf("2012-10-8"))
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(closedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitsUsedDemoDaten.getRpm5_timeUnitsUsedList())
                .setTimeUnitEstimated(issueTimeUnitEstimatedDemoDaten.getRpm5_timeUnitEstimated())
                .getIssueBase();
        planningUnit1_1_issueBaseList.add(rpm_5_issue);

        final IssueBase rpm_6_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 6")
                .setDueDateClosed(Date.valueOf("2012-10-7"))
                .setDueDatePlanned(Date.valueOf("2012-10-8"))
                .setDueDateResolved(null)
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(resolvedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitsUsedDemoDaten.getRpm6_timeUnitsUsedList())
                .setTimeUnitEstimated(issueTimeUnitEstimatedDemoDaten.getRpm6_timeUnitEstimated())
                .getIssueBase();
        planningUnit1_2_issueBaseList.add(rpm_6_issue);

        final IssueBase rpm_7_issue = new IssueBaseBuilder()
                .setAssignee(benutzerDemoDaten.getDanielMacDonaldBenutzer())
                .setText("Test Issue 7")
                .setDueDateClosed(Date.valueOf("2012-10-10"))
                .setDueDatePlanned(Date.valueOf("2012-10-10"))
                .setDueDateResolved(Date.valueOf("2012-10-11"))
                .setReporter(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setStatus(resolvedIssueStatus)
                .setTimeUnitsUsed(issueTimeUnitsUsedDemoDaten.getRpm7_timeUnitsUsedList())
                .setTimeUnitEstimated(issueTimeUnitEstimatedDemoDaten.getRpm7_timeUnitEstimated())
                .getIssueBase();
        planningUnit2_issueBaseList.add(rpm_7_issue);

        issueWithSubIssues = rpm_2_issue;
        issueWithoutSubIssues = rpm_1_issue;
        issueWithTimeUnitsEstimatedNull = new IssueBaseBuilder()
                .setTimeUnitEstimated(null)
                .getIssueBase();
        issueWithTimeUnitsUnisUsedNull = new IssueBaseBuilder()
                .setTimeUnitsUsed(new ArrayList<IssueTimeUnit>())
                .getIssueBase();
        issueWithEmptyTimeUnitEstimated = new IssueBaseBuilder()
                .setTimeUnitEstimated(new IssueTimeUnit())
                .getIssueBase();
        issueWithEmptyTimeUnitsUsed = new IssueBaseBuilder()
                .setTimeUnitsUsed(new ArrayList<IssueTimeUnit>())
                .getIssueBase();
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

    public IssueBase getIssueWithSubIssues() {
        return issueWithSubIssues;
    }

    public IssueBase getIssueWithoutSubIssues() {
        return issueWithoutSubIssues;
    }

    public IssueBase getIssueWithTimeUnitsEstimatedNull() {
        return issueWithTimeUnitsEstimatedNull;
    }

    public IssueBase getIssueWithTimeUnitsUnisUsedNull() {
        return issueWithTimeUnitsUnisUsedNull;
    }

    public IssueBase getIssueWithEmptyTimeUnitEstimated() {
        return issueWithEmptyTimeUnitEstimated;
    }

    public IssueBase getIssueWithEmptyTimeUnitsUsed() {
        return issueWithEmptyTimeUnitsUsed;
    }
}
