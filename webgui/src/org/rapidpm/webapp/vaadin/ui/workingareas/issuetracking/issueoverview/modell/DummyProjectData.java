package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import com.vaadin.data.util.BeanItemContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;
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
    public static final String PROPERTY_NAME = "planningUnitName";
    private static final Projekt project = new ProjektBean(1).getProjekte().get(0);

    public  static Projekt getProject() {
        return project;
    }

    public static List<PlanningUnitGroup> getPlannungUnitGroups() {
        return project.getPlanningUnitGroups();
    }

    public static BeanItemContainer<PlanningUnit> getPlanningUnitContainer() {

        List<PlanningUnit> planningUnits = new ArrayList<>();
        for (PlanningUnitGroup pug : project.getPlanningUnitGroups()) {
            planningUnits.addAll(pug.getPlanningUnitList());
        }

        final BeanItemContainer<PlanningUnit> planningUnitBeanItemContainer
                = new BeanItemContainer<PlanningUnit>(PlanningUnit.class,  planningUnits);
        return planningUnitBeanItemContainer;
    }
}
