package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.tabsheet.sheets;

import com.vaadin.ui.Table;
import com.vaadin.ui.TreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.tabsheet.AbstractControllingTabLayout;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 14.11.12
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public class ControllingPlanningUnitTab extends AbstractControllingTabLayout {

    private final Table totalControllingDataTable;
    private final TreeTable subPlanningUnitsControllingDataTreeTable;
    private final TreeTable subIssuesControllingDataTreeTable;

    public ControllingPlanningUnitTab(){
        super("PlanningUnit Dauer/Kosten");

        totalControllingDataTable = new Table("Gesamt");
        subPlanningUnitsControllingDataTreeTable = new TreeTable("Unterplaneinheiten");
        subIssuesControllingDataTreeTable = new TreeTable("Unteraufgaben");

        addComponent(totalControllingDataTable);
        addComponent(subPlanningUnitsControllingDataTreeTable);
        addComponent(subIssuesControllingDataTreeTable);
        setSizeFull();
    }


}
