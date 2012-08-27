package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;

import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.Benutzer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.IssuePriority;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.IssueStatus;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 14.08.12
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class ProjektBean {


    private Projekt projekt;

    private final String[] issueStatuses = new String[]{"Open","Closed","InProgress","OnHold","Resolved"};
    private final String[] issuePriorities = new String[]{"Blocker","Major","Minor","Trivial","Critical"};
    public static final String[] issueUsers = new String[]{"franzschumacher","herbertknoll","gustavheinrich","brigitteknauf","svenjarahmer"};
    private final IssueStatus openStatus = new IssueStatus();
    private final IssueStatus closedStatus = new IssueStatus();
    private final IssueStatus resolvedStatus = new IssueStatus();
    private final IssueStatus onHoldStatus = new IssueStatus();
    private final IssueStatus inProgressStatus = new IssueStatus();

    public ProjektBean(RessourceGroupsBean ressourceGroupsBean) {
        addData(ressourceGroupsBean);
    }

    private void addData(RessourceGroupsBean ressourceGroupsBean)
    {
        projekt = new Projekt();
        projekt.setProjektName("Projekt 1");

        final ArrayList<PlanningUnitGroup> planningUnitGroups = new ArrayList<>();
        final PlanningUnitGroup vorbereitungen = new PlanningUnitGroup();
        final PlanningUnitGroup projektworkshop = new PlanningUnitGroup();
        final PlanningUnitGroup angebotserstellung = new PlanningUnitGroup();
        final PlanningUnitGroup realisierungMandantengruppe = new PlanningUnitGroup();
        final PlanningUnitGroup realisierungDatenKollektieren = new PlanningUnitGroup();
        final PlanningUnitGroup vorbereitenDesReporting = new PlanningUnitGroup();
        final PlanningUnitGroup projektmanagement = new PlanningUnitGroup();
        final PlanningUnitGroup kommunikation = new PlanningUnitGroup();
        final PlanningUnitGroup abschlussarbeiten = new PlanningUnitGroup();
        final PlanningUnitGroup schulungen = new PlanningUnitGroup();
        vorbereitungen.setPlanningUnitName("Vorbereitungen");
        vorbereitungen.setIssueBase(createIssueBase());
        projektworkshop.setPlanningUnitName("projektworkshop");
        projektworkshop.setIssueBase(createIssueBase());
        angebotserstellung.setPlanningUnitName("angebotserstellung");
        angebotserstellung.setIssueBase(createIssueBase());
        realisierungMandantengruppe.setPlanningUnitName("Realisierung Mandantengruppe");
        realisierungMandantengruppe.setIssueBase(createIssueBase());
        realisierungDatenKollektieren.setPlanningUnitName("Realisierung / Daten kollektieren");
        realisierungDatenKollektieren.setIssueBase(createIssueBase());
        vorbereitenDesReporting.setPlanningUnitName("Vorbereiten des Reporting");
        vorbereitenDesReporting.setIssueBase(createIssueBase());
        projektmanagement.setPlanningUnitName("Projektmanagement");
        projektmanagement.setIssueBase(createIssueBase());
        kommunikation.setPlanningUnitName("Kommunikation");
        kommunikation.setIssueBase(createIssueBase());
        abschlussarbeiten.setPlanningUnitName("abschlussarbeiten");
        abschlussarbeiten.setIssueBase(createIssueBase());
        schulungen.setPlanningUnitName("schulungen");
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

        for(PlanningUnitGroup planningUnitGroup : planningUnitGroups){
            planningUnitGroup.setPlanningUnitList(new ArrayList<PlanningUnit>());
        }
        //--PlanningUnitGroup: Vorbereitungen mit PlanningUnits füllen (welche mit PlanningUnitElements gefüllt werden)
        final ArrayList<PlanningUnit> planningUnitsVorbereitungen = new ArrayList<>();

        String[] planningUnitsArray = {"Erstkontakt vor Ort","Gesprächsvorbereitung","Präsentation","Gesprächsbestätigung"};
        for(final String planningUnitName : Arrays.asList(planningUnitsArray)){  //für jede unterzeile
            final PlanningUnit planningUnit = new PlanningUnit();
            planningUnit.setPlanningUnitElementName(planningUnitName);
            planningUnit.setIssueBase(createIssueBase());
            final ArrayList<PlanningUnitElement> planningUnitElements = new ArrayList<>();
            for(final RessourceGroup ressourceGroup : ressourceGroupsBean.getRessourceGroups())          //für jede zelle
            {
                PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                planningUnitElement.setPlannedDays((int) (Math.random()*10));
                planningUnitElement.setPlannedHours((int) (Math.random()*24));
                planningUnitElement.setPlannedMinutes((int) (Math.random()*60));
                planningUnitElement.setRessourceGroup(ressourceGroup);
                planningUnitElements.add(planningUnitElement);
            }
            planningUnit.setPlanningUnitElementList(planningUnitElements);
            planningUnitsVorbereitungen.add(planningUnit);
        }
        //Erstkontakt vor Ort kinder übergeben
        for(final PlanningUnit planningUnit : planningUnitsVorbereitungen){
            if(planningUnit.getPlanningUnitElementName().equals("Erstkontakt vor Ort")){
                ArrayList<PlanningUnit> childPlanningUnits = new ArrayList<>();
                PlanningUnit childPlanningUnit1 = new PlanningUnit();
                childPlanningUnit1.setPlanningUnitElementName("Person A kontaktieren");
                childPlanningUnit1.setIssueBase(createIssueBase());
                final ArrayList<PlanningUnitElement> planningUnitElements1 = new ArrayList<>();
                for(final RessourceGroup ressourceGroup : ressourceGroupsBean.getRessourceGroups())          //für jede zelle
                {
                    PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                    planningUnitElement.setPlannedDays((int) (Math.random()*10));
                    planningUnitElement.setPlannedHours((int) (Math.random()*24));
                    planningUnitElement.setPlannedMinutes((int) (Math.random()*60));
                    planningUnitElement.setRessourceGroup(ressourceGroup);
                    planningUnitElements1.add(planningUnitElement);
                }
                childPlanningUnit1.setPlanningUnitElementList(planningUnitElements1);

                PlanningUnit childPlanningUnit2 = new PlanningUnit();
                childPlanningUnit2.setPlanningUnitElementName("Person B kontaktieren");
                childPlanningUnit2.setIssueBase(createIssueBase());
                final ArrayList<PlanningUnitElement> planningUnitElements2 = new ArrayList<>();
                for(final RessourceGroup ressourceGroup : ressourceGroupsBean.getRessourceGroups())          //für jede zelle
                {
                    PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                    planningUnitElement.setPlannedDays((int) (Math.random()*10));
                    planningUnitElement.setPlannedHours((int) (Math.random()*24));
                    planningUnitElement.setPlannedMinutes((int) (Math.random()*60));
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
        IssueBase issueBase = new IssueBase();
        IssueStatus issueStatus = new IssueStatus();
        IssuePriority issuePriority = new IssuePriority();
        Benutzer reporter = new Benutzer();
        Benutzer assignee = new Benutzer();
        Date plannedDate = new Date();
        Date resolvedDate = new Date();
        Date closedDate = new Date();
        issueStatus.setStatusName(issueStatuses[(int)(Math.random() * (issueStatuses.length))]);
        issuePriority.setPriorityName(issuePriorities[(int)(Math.random() * (issuePriorities.length))]);
        reporter.setLogin(issueUsers[(int)(Math.random() * (issueUsers.length))]);
        assignee.setLogin(issueUsers[(int)(Math.random() * (issueUsers.length))]);
        issueBase.setIssueStatus(issueStatus);
        issueBase.setIssuePriority(issuePriority);
        issueBase.setReporter(reporter);
        issueBase.setAssignee(assignee);
        issueBase.setDueDate_planned(plannedDate);
        issueBase.setDueDate_resolved(resolvedDate);
        issueBase.setDueDate_closed(closedDate);
        return issueBase;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }
}
