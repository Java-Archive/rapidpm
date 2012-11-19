package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.tabsheet.sheets;

import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.tabsheet.AbstractControllingTabLayout;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 12.11.12
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class ControllingRessourceGroupsTab extends AbstractControllingTabLayout {

    private final Table ressourceGroupControllingTable;

    public ControllingRessourceGroupsTab() {
        super("Ressourcengruppen");

        ressourceGroupControllingTable = new Table("Ressourcengruppen Controlling");
        addComponent(ressourceGroupControllingTable);
        setSizeFull();
    }
}
