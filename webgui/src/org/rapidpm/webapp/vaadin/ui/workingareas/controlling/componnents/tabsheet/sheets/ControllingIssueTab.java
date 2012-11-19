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
public class ControllingIssueTab extends AbstractControllingTabLayout {

    private final Table totalControllingDataTable;
    private final TreeTable processingStepControllingDataTable;
    private final TreeTable subIssuesControllingDataTreeTable;

    public ControllingIssueTab(){
        super("Issue Dauer/Kosten");

        totalControllingDataTable = new Table("Gesamt");
        processingStepControllingDataTable = new TreeTable("Bearbeitungsvorgänge");
        subIssuesControllingDataTreeTable = new TreeTable("Unteraufgaben");

        addComponent(totalControllingDataTable);
        addComponent(processingStepControllingDataTable);
        addComponent(subIssuesControllingDataTreeTable);
        setSizeFull();
    }

}
