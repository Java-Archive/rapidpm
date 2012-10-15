package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.components;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Button;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.TreeTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 05.09.12
 * Time: 08:47
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class UndoButton extends Button implements Button.ClickListener {

    private MyTreeTable treeTable;
    private HierarchicalContainer dataSource;
    private CostsScreen screen;

    public UndoButton(final CostsScreen screen, final MyTreeTable treeTable, final HierarchicalContainer dataSource){
        this.screen = screen;
        this.treeTable = treeTable;
        this.dataSource = dataSource;
        this.addClickListener(this);
        this.setCaption("remove sortorder");
        this.setStyleName("link");
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        final TreeTableFiller treeTableFiller = new TreeTableFiller(screen.getMessagesBundle(), screen, treeTable,
                dataSource);
        treeTableFiller.fill();
        treeTable.markAsDirty();

        this.setVisible(false);
    }
}
