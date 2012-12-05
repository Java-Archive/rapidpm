package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model;

import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 03.12.12
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public class ModelSingleton {

    private static final HashMap<PlannedProject, TreeIssueBaseContainer> instanceMap = new HashMap<>();

    public static TreeIssueBaseContainer getInstance(PlannedProject project) {
        if (!instanceMap.containsKey(project)) {
            instanceMap.put(project, new TreeIssueBaseContainer(project));
        }
        return instanceMap.get(project);
    }
}
