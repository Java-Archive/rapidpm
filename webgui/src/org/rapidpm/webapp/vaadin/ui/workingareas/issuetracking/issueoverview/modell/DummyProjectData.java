package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import com.vaadin.data.util.BeanItemContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 27.09.12
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
public class DummyProjectData {

    public final static String PROPERTY_CAPTION = "caption";
    private static Projekt project = new ProjektBean(1).getProjekte().get(0);


    public static void resetProjekt() {
        project = new ProjektBean(1).getProjekte().get(0);
    }

    public static List<PlanningUnit> getPlanningUnitList() {
        resetProjekt();
        return project.getPlanningUnits();
    }

    public static List<String> getTypeList() {
        List<String> statusList = new ArrayList<>();
        statusList.add("Bug");
        statusList.add("Task");
        statusList.add("Improvement");
        statusList.add("New Function");
        statusList.add("Epic");
        statusList.add("Story");
        return statusList;
    }

    public static List<IssueStatus> getStatusList() {
        List<IssueStatus> statusList = new ArrayList<>();
        statusList.add(new IssueStatus("Open"));
        statusList.add(new IssueStatus("InProgress"));
        statusList.add(new IssueStatus("Resolved"));
        statusList.add(new IssueStatus("Closed"));
        statusList.add(new IssueStatus("OnHold"));
        return statusList;
    }

    public static List<IssuePriority> getPriorityList() {
        List<IssuePriority> priorityList = new ArrayList<>();
        priorityList.add(new IssuePriority(0, "Trivial"));
        priorityList.add(new IssuePriority(0, "Minor"));
        priorityList.add(new IssuePriority(0, "Major"));
        priorityList.add(new IssuePriority(0, "Critical"));
        priorityList.add(new IssuePriority(0, "Blocker"));
        return priorityList;
    }

    public static Integer[] getStoryPointArray() {
        return new Integer[] {1,2,3,4,5,6,7,8,9,10};
    }

    public static String[] getVersionArray() {
        return new String[] {"1.0", "2.0"};
    }
}
