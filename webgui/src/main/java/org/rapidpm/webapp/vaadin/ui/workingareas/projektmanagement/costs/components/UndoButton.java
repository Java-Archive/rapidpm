package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.components;

import com.vaadin.flow.component.button.Button;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 05.09.12
 * Time: 08:47
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class UndoButton extends Button {

    private MyTreeTable treeTable;
//    private HierarchicalContainer dataSource;
    private CostsScreen screen;

    public UndoButton(final CostsScreen screen, final MyTreeTable treeTable, final String dataSource){
        this.screen = screen;
        this.treeTable = treeTable;
        this.setText("remove sortorder");
    }
}
