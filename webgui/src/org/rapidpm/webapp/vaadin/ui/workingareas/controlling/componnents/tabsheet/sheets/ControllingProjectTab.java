package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.tabsheet.sheets;

import com.vaadin.ui.Table;
import com.vaadin.ui.TreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.tabsheet.AbstractControllingTabLayout;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 12.11.12
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class ControllingProjectTab extends AbstractControllingTabLayout {

    private final Table totalControllingDataTable;
    private final TreeTable subPlanningUnitsControllingDataTreeTable;

    public ControllingProjectTab(){
        super("Planned Project Dauer/Kosten");

        totalControllingDataTable = new Table("Gesamt");
        subPlanningUnitsControllingDataTreeTable = new TreeTable("Unterplaneinheiten");

        addComponent(totalControllingDataTable);
        addComponent(subPlanningUnitsControllingDataTreeTable);
        setSizeFull();
    }

}
