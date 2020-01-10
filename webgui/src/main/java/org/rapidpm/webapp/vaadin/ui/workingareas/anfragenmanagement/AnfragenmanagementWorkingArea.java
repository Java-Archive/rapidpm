package org.rapidpm.webapp.vaadin.ui.workingareas.anfragenmanagement;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 12:14
 */

public class AnfragenmanagementWorkingArea extends VerticalLayout {

    public AnfragenmanagementWorkingArea() {
        final Grid table = new Grid();
//        table.setVisibleColumns(ProjektanfrageUI.VISIBLE_COLUMNS);
//        table.setColumnHeaders(ProjektanfrageUI.COLUMN_NAMES);
//        table.setSelectable(true);
//        table.setColumnCollapsingAllowed(true);
        table.setColumnReorderingAllowed(true);
        table.setSizeFull();
        add(table);
    }

}
