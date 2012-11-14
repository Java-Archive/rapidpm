package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 12.11.12
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class ControllingProjectTab extends VerticalLayout {

    private final Table totalControllingDataTable;
    private final TreeTable subPlanningUnitsControllingDataTreeTable;

    public ControllingProjectTab(){
        setSizeFull();

        totalControllingDataTable = new Table("Gesamt");
        subPlanningUnitsControllingDataTreeTable = new TreeTable("Unterplaneinheiten");

        addComponent(totalControllingDataTable);
        addComponent(subPlanningUnitsControllingDataTreeTable);
    }

}
