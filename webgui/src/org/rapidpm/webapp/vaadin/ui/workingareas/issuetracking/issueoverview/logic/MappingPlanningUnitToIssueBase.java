package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.ProjectDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStoryPoint;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 10.01.13
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
public class MappingPlanningUnitToIssueBase {

    private final PlannedProject project;
    private final IssueBaseDAO dao;

    public MappingPlanningUnitToIssueBase(final PlannedProject project) {
        if (project == null)
            throw new NullPointerException("Project is null");

        this.project = project;
        dao = DaoFactorySingelton.getInstance().getIssueBaseDAO(2L);
    }

    public void startMapping() {
        PlannedProject pro = DaoFactorySingelton.getInstance().getPlannedProjectDAO().findByID(2L);

        for (final PlanningUnit pu : pro.getPlanningUnits()) {
            if (pu.getParent() == null) {
                mapPlanningUnitToIssue(pu, pro.getId());
            }
            System.out.println(pu);
        }
    }

    private IssueBase mapPlanningUnitToIssue(final PlanningUnit pu, final Long projectid) {
        IssueBase issue = new IssueBase(projectid);
        issue.setSummary(pu.getPlanningUnitName());
        issue.setAssignee(pu.getResponsiblePerson());
        issue.setStory(pu.getDescriptions().toString());
        issue.setStoryPoints(new IssueStoryPoint(pu.getEstimatedStoryPoints()));
        Set<PlanningUnit> children = pu.getKindPlanningUnits();
        if (!children.isEmpty()) {
            for (final PlanningUnit childPu : children) {
                issue.addSubIssue(mapPlanningUnitToIssue(childPu, projectid));
            }
        }
        issue = dao.persist(issue);
        System.out.println(issue);
        return issue;
    }

}
