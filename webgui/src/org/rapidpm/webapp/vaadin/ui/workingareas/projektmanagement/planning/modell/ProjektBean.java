package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitElement;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.rapidpm.Constants.HOURS_DAY;
import static org.rapidpm.Constants.MINS_HOUR;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 14.08.12
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class ProjektBean {


    private static final int MAXDAYS = 10;

    private Projekt projekt;

    private int[] storyPoints = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final String[] issueStatuses = new String[]{"Open", "Closed", "InProgress", "OnHold", "Resolved"};
    private final String[] issuePriorities = new String[]{"Blocker", "Major", "Minor", "Trivial", "Critical"};
    //REFAC entfernen
    public static final String[] issueUsers = new String[]{"franzschumacher", "herbertknoll", "gustavheinrich", "brigitteknauf", "svenjarahmer"};

//    private final IssueStatus openStatus = new IssueStatus();
//    private final IssueStatus closedStatus = new IssueStatus();
//    private final IssueStatus resolvedStatus = new IssueStatus();
//    private final IssueStatus onHoldStatus = new IssueStatus();
//    private final IssueStatus inProgressStatus = new IssueStatus();

    public ProjektBean(RessourceGroupsBean ressourceGroupsBean) {
        addData(ressourceGroupsBean);
    }

    //REFAC auslagern so das der Code schnell entfernt werden kann
    private void addData(RessourceGroupsBean ressourceGroupsBean) {
        projekt = new Projekt();
        projekt.setProjektName("Projekt 1");
        projekt.setRessourceGroups(ressourceGroupsBean.getRessourceGroups());

        final ArrayList<PlanningUnitGroup> planningUnitGroups = new ArrayList<>();
        final PlanningUnitGroup vorbereitungen = new PlanningUnitGroup(ressourceGroupsBean.getRessourceGroups());
        final PlanningUnitGroup projektworkshop = new PlanningUnitGroup(ressourceGroupsBean.getRessourceGroups());
        final PlanningUnitGroup angebotserstellung = new PlanningUnitGroup(ressourceGroupsBean.getRessourceGroups());
        final PlanningUnitGroup realisierungMandantengruppe = new PlanningUnitGroup(ressourceGroupsBean.getRessourceGroups());
        final PlanningUnitGroup realisierungDatenKollektieren = new PlanningUnitGroup(ressourceGroupsBean.getRessourceGroups());
        final PlanningUnitGroup vorbereitenDesReporting = new PlanningUnitGroup(ressourceGroupsBean.getRessourceGroups());
        final PlanningUnitGroup projektmanagement = new PlanningUnitGroup(ressourceGroupsBean.getRessourceGroups());
        final PlanningUnitGroup kommunikation = new PlanningUnitGroup(ressourceGroupsBean.getRessourceGroups());
        final PlanningUnitGroup abschlussarbeiten = new PlanningUnitGroup(ressourceGroupsBean.getRessourceGroups());
        final PlanningUnitGroup schulungen = new PlanningUnitGroup(ressourceGroupsBean.getRessourceGroups());
        vorbereitungen.setPlanningUnitGroupName("Vorbereitungen");
        vorbereitungen.setIssueBase(createIssueBase());
        projektworkshop.setPlanningUnitGroupName("projektworkshop");
        projektworkshop.setIssueBase(createIssueBase());
        angebotserstellung.setPlanningUnitGroupName("angebotserstellung");
        angebotserstellung.setIssueBase(createIssueBase());
        realisierungMandantengruppe.setPlanningUnitGroupName("Realisierung Mandantengruppe");
        realisierungMandantengruppe.setIssueBase(createIssueBase());
        realisierungDatenKollektieren.setPlanningUnitGroupName("Realisierung / Daten kollektieren");
        realisierungDatenKollektieren.setIssueBase(createIssueBase());
        vorbereitenDesReporting.setPlanningUnitGroupName("Vorbereiten des Reporting");
        vorbereitenDesReporting.setIssueBase(createIssueBase());
        projektmanagement.setPlanningUnitGroupName("Projektmanagement");
        projektmanagement.setIssueBase(createIssueBase());
        kommunikation.setPlanningUnitGroupName("Kommunikation");
        kommunikation.setIssueBase(createIssueBase());
        abschlussarbeiten.setPlanningUnitGroupName("abschlussarbeiten");
        abschlussarbeiten.setIssueBase(createIssueBase());
        schulungen.setPlanningUnitGroupName("schulungen");
        schulungen.setIssueBase(createIssueBase());

        planningUnitGroups.add(vorbereitungen);
        planningUnitGroups.add(projektworkshop);
        planningUnitGroups.add(angebotserstellung);
        planningUnitGroups.add(realisierungMandantengruppe);
        planningUnitGroups.add(realisierungDatenKollektieren);
        planningUnitGroups.add(vorbereitenDesReporting);
        planningUnitGroups.add(projektmanagement);
        planningUnitGroups.add(kommunikation);
        planningUnitGroups.add(abschlussarbeiten);
        planningUnitGroups.add(schulungen);

        for (PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
            planningUnitGroup.setPlanningUnitList(new ArrayList<PlanningUnit>());
        }
        //--PlanningUnitGroup: Vorbereitungen mit PlanningUnits füllen (welche mit PlanningUnitElements gefüllt werden)
        final ArrayList<PlanningUnit> planningUnitsVorbereitungen = new ArrayList<>();

        String[] planningUnitsArray = {"Erstkontakt vor Ort", "Gesprächsvorbereitung", "Präsentation", "Gesprächsbestätigung"};
        for (final String planningUnitName : Arrays.asList(planningUnitsArray)) {  //für jede unterzeile
            final PlanningUnit planningUnit = new PlanningUnit();
            planningUnit.setPlanningUnitName(planningUnitName);
            planningUnit.setIssueBase(createIssueBase());
            final ArrayList<PlanningUnitElement> planningUnitElements = new ArrayList<>();
            for (final RessourceGroup ressourceGroup : ressourceGroupsBean.getRessourceGroups())          //für jede zelle
            {
                final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                planningUnitElement.setPlannedDays((int) (Math.random() * MAXDAYS));
                planningUnitElement.setPlannedHours((int) (Math.random() * HOURS_DAY));
                planningUnitElement.setPlannedMinutes((int) (Math.random() * MINS_HOUR));
                planningUnitElement.setRessourceGroup(ressourceGroup);
                planningUnitElements.add(planningUnitElement);
            }
            planningUnit.setPlanningUnitElementList(planningUnitElements);
            planningUnitsVorbereitungen.add(planningUnit);
        }
        //Erstkontakt vor Ort kinder übergeben
        for (final PlanningUnit planningUnit : planningUnitsVorbereitungen) {
            if (planningUnit.getPlanningUnitName().equals("Erstkontakt vor Ort")) {
                final ArrayList<PlanningUnit> childPlanningUnits = new ArrayList<>();
                final PlanningUnit childPlanningUnit1 = new PlanningUnit();
                childPlanningUnit1.setPlanningUnitName("Person A kontaktieren");
                childPlanningUnit1.setIssueBase(createIssueBase());
                final ArrayList<PlanningUnitElement> planningUnitElements1 = new ArrayList<>();
                for (final RessourceGroup ressourceGroup : ressourceGroupsBean.getRessourceGroups())          //für jede zelle
                {
                    PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                    planningUnitElement.setPlannedDays((int) (Math.random() * MAXDAYS));
                    planningUnitElement.setPlannedHours((int) (Math.random() * HOURS_DAY));
                    planningUnitElement.setPlannedMinutes((int) (Math.random() * MINS_HOUR));
                    planningUnitElement.setRessourceGroup(ressourceGroup);
                    planningUnitElements1.add(planningUnitElement);
                }
                childPlanningUnit1.setPlanningUnitElementList(planningUnitElements1);

                final PlanningUnit childPlanningUnit2 = new PlanningUnit();
                childPlanningUnit2.setPlanningUnitName("Person B kontaktieren");
                childPlanningUnit2.setIssueBase(createIssueBase());
                final ArrayList<PlanningUnitElement> planningUnitElements2 = new ArrayList<>();
                for (final RessourceGroup ressourceGroup : ressourceGroupsBean.getRessourceGroups())          //für jede zelle
                {
                    PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                    planningUnitElement.setPlannedDays((int) (Math.random() * MAXDAYS));
                    planningUnitElement.setPlannedHours((int) (Math.random() * HOURS_DAY));
                    planningUnitElement.setPlannedMinutes((int) (Math.random() * MINS_HOUR));
                    planningUnitElement.setRessourceGroup(ressourceGroup);
                    planningUnitElements2.add(planningUnitElement);
                }
                childPlanningUnit2.setPlanningUnitElementList(planningUnitElements2);
                childPlanningUnits.add(childPlanningUnit1);
                childPlanningUnits.add(childPlanningUnit2);
                planningUnit.setKindPlanningUnits(childPlanningUnits);

            }
        }
        vorbereitungen.setPlanningUnitList(planningUnitsVorbereitungen);
        projekt.setPlanningUnitGroups(planningUnitGroups);


    }

    private IssueBase createIssueBase() {
        final IssueBase issueBase = new IssueBase();
        final Integer storyPoints = this.storyPoints[(int) (Math.random() * (this.storyPoints.length))];
        final IssueStatus issueStatus = new IssueStatus();
        final IssuePriority issuePriority = new IssuePriority();
        final Benutzer reporter = new Benutzer();
        final Benutzer assignee = new Benutzer();
        final Date plannedDate = new Date();
        final Date resolvedDate = new Date();
        final Date closedDate = new Date();
        final int anzTestCases = (int) (Math.random() * 5);
        final ArrayList<String> testCases = new ArrayList<>();
        for (int i = 0; i < anzTestCases; i++) {
            final String testCase = "Testcase " + (i + 1);
            testCases.add(testCase);
        }
        issueStatus.setStatusName(issueStatuses[(int) (Math.random() * (issueStatuses.length))]);
        issuePriority.setPriorityName(issuePriorities[(int) (Math.random() * (issuePriorities.length))]);
        reporter.setLogin(issueUsers[(int) (Math.random() * (issueUsers.length))]);
        assignee.setLogin(issueUsers[(int) (Math.random() * (issueUsers.length))]);
        issueBase.setText("Dies ist eine Issue-Beschreibung. Ein weiterer Satz der Beschreibung. " +
                "Ein weiterer Satz der Beschreibung. Ein weiterer Satz der Beschreibung.");
        issueBase.setStoryPoints(storyPoints);
        issueBase.setIssueStatus(issueStatus);
        issueBase.setIssuePriority(issuePriority);
        issueBase.setReporter(reporter);
        issueBase.setAssignee(assignee);
        issueBase.setDueDate_planned(plannedDate);
        issueBase.setDueDate_resolved(resolvedDate);
        issueBase.setDueDate_closed(closedDate);
        issueBase.setTestcases(testCases);
        return issueBase;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }
}