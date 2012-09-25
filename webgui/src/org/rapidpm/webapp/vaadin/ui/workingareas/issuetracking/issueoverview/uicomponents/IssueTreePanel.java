package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TreeValueChangeListener;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 20.09.12
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class IssueTreePanel extends Panel{
    private Tree issueTree;

    public IssueTreePanel(IssueDetailsPanel detailsPanel) {
        this.setSizeFull();
        issueTree = new Tree("The Planets and Major Moons");
        issueTree.setImmediate(true);
        issueTree.addValueChangeListener(new TreeValueChangeListener(detailsPanel));
        fillTreeWithIssues();
        addComponent(issueTree);
    }

    private void fillTreeWithIssues() {
        final Object[][] planets = new Object[][]{
                new Object[]{"Mercury"},
                new Object[]{"Venus"},
                new Object[]{"Earth", "The Moon"},
                new Object[]{"Mars", "Phobos", "Deimos"},
                new Object[]{"Jupiter", "Io", "Europa", "Ganymedes",
                        "Callisto"},
                new Object[]{"Saturn",  "Titan", "Tethys", "Dione",
                        "Rhea", "Iapetus"},
                new Object[]{"Uranus",  "Miranda", "Ariel", "Umbriel",
                        "Titania", "Oberon"},
                new Object[]{"Neptune", "Triton", "Proteus", "Nereid",
                        "Larissa"}};

/* Add planets as root items in the tree. */
        for (int i=0; i<planets.length; i++) {
            String planet = (String) (planets[i][0]);
            issueTree.addItem(planet);

            if (planets[i].length == 1) {
                // The planet has no moons so make it a leaf.
                issueTree.setChildrenAllowed(planet, false);
            } else {
                // Add children (moons) under the planets.
                for (int j=1; j<planets[i].length; j++) {
                    String moon = (String) planets[i][j];

                    // Add the item as a regular item.
                    issueTree.addItem(moon);

                    // Set it to be a child.
                    issueTree.setParent(moon, planet);

                    // Make the moons look like leaves.
                    issueTree.setChildrenAllowed(moon, false);
                }

                // Expand the subtree.
                issueTree.expandItemsRecursively(planet);
            }
        }
    }
}
