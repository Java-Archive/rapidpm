package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model;

import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 03.12.12
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public class TreeIssueBaseContainerSingleton {

    private static final Map<PlannedProject, TreeIssueBaseContainer> instanceMap = new HashMap<>();

    public static TreeIssueBaseContainer getInstance(final PlannedProject project) {
        if (project == null)
            throw new NullPointerException("Project must not be null");

        if (!instanceMap.containsKey(project)) {
            instanceMap.put(project, new TreeIssueBaseContainer(project));
        }
        return instanceMap.get(project);
    }
}
