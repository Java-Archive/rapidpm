package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.tabsheet;

import com.vaadin.ui.TabSheet;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.tabsheet.sheets.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 16.11.12
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class ControllingDataTabSheet extends TabSheet {

    private final Map<Class, List<AbstractControllingTabLayout>> controllingSheetMap;
    private final List<Tab> tabList;
    private Class selectedControllingClass;


    public ControllingDataTabSheet() {
        controllingSheetMap = new HashMap<>();
        tabList = new ArrayList<>();
        initControllingSheetMap();
    }

    public void setSelectedControllingClass(Class clazz){
        selectedControllingClass = clazz;
        removeAllComponents();
        List<AbstractControllingTabLayout> sheets = controllingSheetMap.get(clazz);
        for(AbstractControllingTabLayout sheet : sheets){
            Tab addedTab = addTab(sheet, sheet.getCaption());
            addedTab.setVisible(true);
            tabList.add(addedTab);
        }
    }

    private void initControllingSheetMap() {
        ArrayList<AbstractControllingTabLayout> projectList = new ArrayList<>();
        projectList.add(new ControllingProjectTab());
        projectList.add(new ControllingRessourceGroupsTab());
        projectList.add(new ControllingChartTab());
        controllingSheetMap.put(PlannedProject.class, projectList);

        ArrayList<AbstractControllingTabLayout> planningUnitList = new ArrayList<>();
        planningUnitList.add(new ControllingPlanningUnitTab());
        planningUnitList.add(new ControllingRessourceGroupsTab());
        planningUnitList.add(new ControllingChartTab());
        controllingSheetMap.put(PlanningUnit.class, planningUnitList);


        ArrayList<AbstractControllingTabLayout> issueList = new ArrayList<>();
        issueList.add(new ControllingIssueTab());
        issueList.add(new ControllingChartTab());
        controllingSheetMap.put(IssueBase.class, issueList);

    }

}
