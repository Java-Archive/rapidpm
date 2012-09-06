package org.rapidpm.webapp.vaadin.ui.workingareas;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 25.08.12
 * Time: 18:23
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public enum IssueStatusEnum {


        Open("status_open.gif"),
        InProgress("status_inprogress.gif"),
        Resolved("status_resolved.gif"),
        Closed("status_closed.gif"),
        OnHold("status_information.gif");

        private final Resource icon;

        private IssueStatusEnum(final String iconPath) {
            this.icon = new ThemeResource("images/" + iconPath);
        }

        public Resource getIcon() {
            return icon;
        }

}
