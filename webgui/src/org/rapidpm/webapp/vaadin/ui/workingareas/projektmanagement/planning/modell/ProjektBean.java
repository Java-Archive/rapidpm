package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell;

import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.LoginBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    private static long elementId;

    private List<Projekt> projekte = new ArrayList<>();
    private Integer currentProjectIndex = 0;

    private int[] storyPoints = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final String[] issueStatuses = new String[]{"Open", "Closed", "InProgress", "OnHold", "Resolved"};
    private final String[] issuePriorities = new String[]{"Blocker", "Major", "Minor", "Trivial", "Critical"};

    public ProjektBean(Integer projectCount) {
        elementId = 0L;
        LoginBean loginBean = EJBFactory.getEjbInstance(LoginBean.class);
        final DaoFactoryBean baseDaoFactoryBean = loginBean.getDaoFactoryBean();
        final RessourceGroupDAO ressourceGroupDAO = baseDaoFactoryBean.getRessourceGroupDAO();
        final List<RessourceGroup> ressourceGroups = ressourceGroupDAO.loadAllEntities();
        for(Integer i=0; i<projectCount;i++){
            addNewProject(ressourceGroups, "Projekt "+(i+1), (long)(i+1));
        }
    }

    //REFAC auslagern so das der Code schnell entfernt werden kann
    public void addNewProject(List<RessourceGroup> ressourceGroups, String projektName, Long projektId) {
        Projekt projekt = new Projekt();
        projekt.setProjektId(projektId);
        projekt.setProjektName(projektName);

        final ArrayList<PlanningUnit> planningUnits = new ArrayList<>();
        final PlanningUnit vorbereitungen = new PlanningUnit();
        final PlanningUnit projektworkshop = new PlanningUnit();
        final PlanningUnit angebotserstellung = new PlanningUnit();
        final PlanningUnit realisierungMandantengruppe = new PlanningUnit();
        final PlanningUnit realisierungDatenKollektieren = new PlanningUnit();
        final PlanningUnit vorbereitenDesReporting = new PlanningUnit();
        final PlanningUnit projektmanagement = new PlanningUnit();
        final PlanningUnit kommunikation = new PlanningUnit();
        final PlanningUnit abschlussarbeiten = new PlanningUnit();
        final PlanningUnit schulungen = new PlanningUnit();
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

        planningUnits.add(vorbereitungen);
        planningUnits.add(projektworkshop);
        planningUnits.add(angebotserstellung);
        planningUnits.add(realisierungMandantengruppe);
        planningUnits.add(realisierungDatenKollektieren);
        planningUnits.add(vorbereitenDesReporting);
        planningUnits.add(projektmanagement);
        planningUnits.add(kommunikation);
        planningUnits.add(abschlussarbeiten);
        planningUnits.add(schulungen);

        for (final PlanningUnit planningUnit : planningUnits) {
            planningUnit.setKindPlanningUnits(new ArrayList<PlanningUnit>());
        }
        //--PlanningUnit: Vorbereitungen mit PlanningUnits füllen (welche mit PlanningUnitElements gefüllt werden)
        final ArrayList<PlanningUnit> planningUnitsVorbereitungen = new ArrayList<>();

        String[] planningUnitsArray = {"Erstkontakt vor Ort", "Gesprächsvorbereitung", "Präsentation", "Gesprächsbestätigung"};
        for (final String planningUnitName : Arrays.asList(planningUnitsArray)) {  //für jede unterzeile
            final PlanningUnit planningUnit = new PlanningUnit();
            planningUnit.setPlanningUnitName(planningUnitName);
            planningUnit.setIssueBase(createIssueBase());
            final ArrayList<PlanningUnitElement> planningUnitElements = new ArrayList<>();
            for (final RessourceGroup ressourceGroup : ressourceGroups)          //für jede zelle
            {
                final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                planningUnitElement.setPlannedDays((int) (Math.random() * MAXDAYS));
                planningUnitElement.setPlannedHours((int) (Math.random() * HOURS_DAY));
                planningUnitElement.setPlannedMinutes((int) (Math.random() * MINS_HOUR));
                planningUnitElement.setRessourceGroup(ressourceGroup);
                planningUnitElement.setId(elementId++);
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
                for (final RessourceGroup ressourceGroup : ressourceGroups)          //für jede zelle
                {
                    PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                    planningUnitElement.setPlannedDays((int) (Math.random() * MAXDAYS));
                    planningUnitElement.setPlannedHours((int) (Math.random() * HOURS_DAY));
                    planningUnitElement.setPlannedMinutes((int) (Math.random() * MINS_HOUR));
                    planningUnitElement.setRessourceGroup(ressourceGroup);
                    planningUnitElement.setId(elementId++);
                    planningUnitElements1.add(planningUnitElement);
                }
                childPlanningUnit1.setPlanningUnitElementList(planningUnitElements1);

                final PlanningUnit childPlanningUnit2 = new PlanningUnit();
                childPlanningUnit2.setPlanningUnitName("Person B kontaktieren");
                childPlanningUnit2.setIssueBase(createIssueBase());
                final ArrayList<PlanningUnitElement> planningUnitElements2 = new ArrayList<>();
                for (final RessourceGroup ressourceGroup : ressourceGroups)          //für jede zelle
                {
                    PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                    planningUnitElement.setPlannedDays((int) (Math.random() * MAXDAYS));
                    planningUnitElement.setPlannedHours((int) (Math.random() * HOURS_DAY));
                    planningUnitElement.setPlannedMinutes((int) (Math.random() * MINS_HOUR));
                    planningUnitElement.setRessourceGroup(ressourceGroup);
                    planningUnitElement.setId(elementId++);
                    planningUnitElements2.add(planningUnitElement);
                }
                childPlanningUnit2.setPlanningUnitElementList(planningUnitElements2);
                childPlanningUnits.add(childPlanningUnit1);
                childPlanningUnits.add(childPlanningUnit2);
                planningUnit.setKindPlanningUnits(childPlanningUnits);

            }
        }
        vorbereitungen.setKindPlanningUnits(planningUnitsVorbereitungen);
        projekt.setPlanningUnits(planningUnits);

        projekte.add(projekt);


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

    public List<Projekt> getProjekte() {
        return projekte;
    }

    public Integer getCurrentProjectIndex() {
        return currentProjectIndex;
    }

    public void setCurrentProjectIndex(Integer currentProjectIndex) {
        this.currentProjectIndex = currentProjectIndex;
    }
}
