package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektplanung.enums;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 26.08.12
 * Time: 13:16
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public enum IssuePriorities {
    Blocker("priority_blocker.gif"),
    Critical("priority_critical.gif"),
    Major("priority_major.gif"),
    Trivial("priority_trivial.gif"),
    Minor("priority_minor.gif");

    private final Resource icon;

    private IssuePriorities(final String iconPath) {
        this.icon = new ThemeResource("images/" + iconPath);
    }

    public Resource getIcon() {
        return icon;
    }
}
