package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.UI;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStoryPoint;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStoryPointDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTestCase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.webapp.vaadin.BaseUI;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 10.01.13
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
public class MappingPlanningUnitToIssueBase {

    private final PlannedProject project;
    private final DaoFactory daoFactory;
    private final IssueBaseDAO dao;

    public MappingPlanningUnitToIssueBase(final PlannedProject project) {
        if (project == null)
            throw new NullPointerException("Project is null");

        this.project = project;
        this.daoFactory = DaoFactorySingleton.getInstance();
        this.dao = daoFactory.getIssueBaseDAO();
    }

    public boolean startMapping() {
        boolean puToMap = false;
        for (final PlanningUnit pu : project.getTopLevelPlanningUnits()) {
            puToMap = true;
            if (pu.getParent() == null) {
                mapPlanningUnitToIssue(pu);
            }
        }
        return puToMap;
    }

    private IssueBase mapPlanningUnitToIssue(final PlanningUnit pu) {
        IssueBase issue = new IssueBase(project.getId());
        issue.setPlanningUnit(pu);
        issue.setSummary(pu.getPlanningUnitName());
//        issue.setReporter(project.getResponsiblePerson());
        final BaseUI current = (BaseUI) UI.getCurrent();
        issue.setReporter(current.getCurrentUser());
        issue.setAssignee(pu.getResponsiblePerson());
        String story = "";
        for (final TextElement txtelement : pu.getDescriptions()) {
            story += txtelement.getText() + "\n";
        }
        issue.setStory(story);


        final IssueStoryPointDAO storyPointDAO = daoFactory.getIssueStoryPointDAO();
        final IssueStoryPoint exist = storyPointDAO.findByName(String.valueOf(pu.getEstimatedStoryPoints()), 0l);
        if (exist != null) {
            issue.setStoryPoints(exist);
        } else {
            IssueStoryPoint stp = new IssueStoryPoint(pu.getEstimatedStoryPoints());
            stp.setProjectId(0l);
            issue.setStoryPoints(storyPointDAO.persist(stp));
        }

        for (final TextElement txtelement : pu.getTestcases()) {
            IssueTestCase testcase = new IssueTestCase(txtelement.getText());
//            issue.addOrChangeTestCase(daoFactory.saveOrUpdateTX(testcase));
        }

        final List<PlanningUnit> children = pu.getKindPlanningUnits();
        if (!children.isEmpty()) {
            for (final PlanningUnit childPu : children) {
                issue.addSubIssue(mapPlanningUnitToIssue(childPu));
            }
        }

        issue.setPriority(daoFactory.getIssuePriorityDAO().loadAllEntities(0l).get(0));
        issue.setStatus(daoFactory.getIssueStatusDAO().loadAllEntities(0l).get(0));
        issue.setType(daoFactory.getIssueTypeDAO().loadAllEntities(0l).get(0));
        issue.setVersion(daoFactory.getIssueVersionDAO().loadAllEntities(0l).get(0));
        issue.setDueDate_planned(new Date());

        issue = dao.persist(issue);
        return issue;
    }

}
